package com.app.maps.handlers;

import java.io.Console;
import java.util.UUID;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCallback;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattDescriptor;
import android.bluetooth.BluetoothManager;
import android.bluetooth.BluetoothProfile;
import android.content.Context;
import android.os.Handler;
import android.widget.Toast;

public class STHandler {

	public UUID UUID_IRT_SERV=UUID.fromString("f000aa00-0451-4000-b000-000000000000");
	public UUID UUID_IRT_DATA=UUID.fromString("f000aa01-0451-4000-b000-000000000000");
	public UUID UUID_IRT_CONF=UUID.fromString("f000aa02-0451-4000-b000-000000000000"); // 0: disable, 1: enable

	public UUID UUID_KEY_SERV=UUID.fromString("0000ffe0-0000-1000-8000-00805f9b34fb");
	public UUID UUID_KEY_DATA=UUID.fromString("0000ffe1-0000-1000-8000-00805f9b34fb");

	public UUID CLIENT_CONFIG_DESCRIPTOR=UUID.fromString("00002902-0000-1000-8000-00805f9b34fb");

	public String DeviceName="SensorTag";
	public BluetoothAdapter BTAdapter; 
	public BluetoothDevice BTDevice;
	public BluetoothGatt BTGatt;

	public boolean scanning;
	public Handler handler;

	public Console console;
	
	private Activity act;
	private double ir_sensor_value;
	
	public STHandler(Activity ay){
		act = ay;
	}
	
	public double getTemp(){
    	return ir_sensor_value;
    }
	
	public void onCreateBluetooth(){
		  BluetoothManager BTManager= (BluetoothManager) act.getSystemService(Context.BLUETOOTH_SERVICE);
	      BTAdapter=BTManager.getAdapter();
	     
	      scanning=false;
	      handler=new Handler();
	}
	
	public void destroy(){
		  if (BTGatt != null)
	      {
	         BTGatt.disconnect();
	         BTGatt.close();
	      }
	}
	
    public BluetoothGattCallback GattCallback = new BluetoothGattCallback()
    {
      int ssstep=0;
      public void SetupSensorStep(BluetoothGatt gatt)
      {
        BluetoothGattCharacteristic characteristic;
        BluetoothGattDescriptor descriptor; 
        switch (ssstep)
        {
          case 0:
            /*
            ** Setup Key Sensor  
            */
             // Enable local notifications
            characteristic=gatt.getService(UUID_KEY_SERV).getCharacteristic(UUID_KEY_DATA);
            gatt.setCharacteristicNotification(characteristic,true);
            // Enabled remote notifications
            descriptor=characteristic.getDescriptor(CLIENT_CONFIG_DESCRIPTOR);
            descriptor.setValue(BluetoothGattDescriptor.ENABLE_NOTIFICATION_VALUE);
            gatt.writeDescriptor(descriptor);
            break;
          case 1:
            /*
            ** Enable IRT Sensor
            */
            characteristic=gatt.getService(UUID_IRT_SERV).getCharacteristic(UUID_IRT_CONF);
            characteristic.setValue(new byte[] {0x01});
            gatt.writeCharacteristic(characteristic);      
            break;
          case 2:
            /*
            ** Setup IRT Sensor 
            */
             // Enable local notifications
            characteristic=gatt.getService(UUID_IRT_SERV).getCharacteristic(UUID_IRT_DATA);
            gatt.setCharacteristicNotification(characteristic,true);
            //Enabled remote notifications
            descriptor=characteristic.getDescriptor(CLIENT_CONFIG_DESCRIPTOR);
            descriptor.setValue(BluetoothGattDescriptor.ENABLE_NOTIFICATION_VALUE);
            gatt.writeDescriptor(descriptor);      
            break;
        }
        ssstep++;
      }
     
      public void onConnectionStateChange(BluetoothGatt gatt, int status, int newState)
      {
        if (status == BluetoothGatt.GATT_SUCCESS && newState == BluetoothProfile.STATE_CONNECTED)
        {
          output("Connected to GATT Server");
          gatt.discoverServices();
        }
        else
        {
          output("Disconnected from GATT Server");
          gatt.disconnect();
          gatt.close();
        }
      }
     
       public void onServicesDiscovered(BluetoothGatt gatt, int status)
       {
         output("Discover & Config GATT Services");
         ssstep=0;
         SetupSensorStep(gatt);
       }
      
       public void onCharacteristicWrite(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic, int status)
       {
         SetupSensorStep(gatt);
       }
      
       public void onDescriptorWrite(BluetoothGatt gatt, BluetoothGattDescriptor descriptor, int status)
       {
         SetupSensorStep(gatt);
       }
        
       public void onCharacteristicChanged(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic)
       {
         if (UUID_KEY_DATA.equals(characteristic.getUuid()))
         {
           int keycode=(characteristic.getIntValue(BluetoothGattCharacteristic.FORMAT_UINT8,0) % 4);
           switch (keycode)
           {
             case 1:
               output("@ Right Key Pressed");
               break;
             case 2:
               output("@ Left Key Pressed");
               break;
             case 3:
               output("@ Both Keys Pressed");
               break;
           }
         }
        
         if (UUID_IRT_DATA.equals(characteristic.getUuid()))
         {
           double ambient=TITOOL.extractAmbientTemperature(characteristic);
           double target=TITOOL.extractTargetTemperature(characteristic,ambient);
           ir_sensor_value = target;
           output("@ "+String.format("%.2f",target)+"&deg;F");
         }
       }
    };
    
    public BluetoothAdapter.LeScanCallback DeviceLeScanCallback=new BluetoothAdapter.LeScanCallback()
    {
      public void onLeScan(final BluetoothDevice device, int rssi, byte[] scanRecord)
      {
        if (DeviceName.equals(device.getName()))
        {
          if (BTDevice == null)
          {
            BTDevice=device;
            BTGatt=BTDevice.connectGatt(act.getApplicationContext(),false,GattCallback);
          }
          else
          {
            if (BTDevice.getAddress().equals(device.getAddress()))
            {
              return;
            }
          }
          output("*<small> "+device.getName()+":"+device.getAddress()+", rssi:"+rssi+"</small>");
        }
      }
    };
   
    public void BTScan()
    {
      if(BTAdapter == null)
      {
        output("No Bluetooth Adapter");
        return;
      }
     
      if (!BTAdapter.isEnabled())
      {
        BTAdapter.enable();
      }

      if (scanning == false)
      {
        handler.postDelayed(new Runnable()
        {
          public void run()
          {
            scanning=false;
            BTAdapter.stopLeScan(DeviceLeScanCallback);
            output("Stop scanning");
          }
        }, 2000);
       
        scanning=true;
        BTDevice=null;
        if (BTGatt != null)
        {
           BTGatt.disconnect();
           BTGatt.close();
        }  
        BTGatt=null;

        BTAdapter.startLeScan(DeviceLeScanCallback);
        output("Start scanning");
      }
    }
   
    public void sysout(String msg)
    {
      System.out.println(msg);
    }
   
    public void message(String msg)
    {
      Toast.makeText(act,msg,Toast.LENGTH_SHORT).show();
    }

    public void output(String msg)
    {
//    	System.out.println(msg);
    }
}

//----------------------------------------------------------------------------------------------------------------------------------------
//Code from TI
class TITOOL
{
	public static double extractAmbientTemperature(BluetoothGattCharacteristic c)
	{
		int offset = 2;
		return shortUnsignedAtOffset(c, offset) / 128.0;
	}

	public static double extractTargetTemperature(BluetoothGattCharacteristic c, double ambient)
	{
		Integer twoByteValue = shortSignedAtOffset(c, 0);

		double Vobj2 = twoByteValue.doubleValue();
		Vobj2 *= 0.00000015625;

		double Tdie = ambient + 273.15;

		double S0 = 5.593E-14;  // Calibration factor
		double a1 = 1.75E-3;
		double a2 = -1.678E-5;
		double b0 = -2.94E-5;
		double b1 = -5.7E-7;
		double b2 = 4.63E-9;
		double c2 = 13.4;
		double Tref = 298.15;
		double S = S0*(1+a1*(Tdie - Tref)+a2*Math.pow((Tdie - Tref),2));
		double Vos = b0 + b1*(Tdie - Tref) + b2*Math.pow((Tdie - Tref),2);
		double fObj = (Vobj2 - Vos) + c2*Math.pow((Vobj2 - Vos),2);
		double tObj = Math.pow(Math.pow(Tdie,4) + (fObj/S),.25);

		return tObj - 273.15;
	}

	public static Integer shortSignedAtOffset(BluetoothGattCharacteristic c, int offset)
	{
		Integer lowerByte = c.getIntValue(BluetoothGattCharacteristic.FORMAT_UINT8, offset);
		Integer upperByte = c.getIntValue(BluetoothGattCharacteristic.FORMAT_SINT8, offset + 1);

		return (upperByte << 8) + lowerByte;
	}

	public static Integer shortUnsignedAtOffset(BluetoothGattCharacteristic c, int offset)
	{
		Integer lowerByte = c.getIntValue(BluetoothGattCharacteristic.FORMAT_UINT8, offset);
		Integer upperByte = c.getIntValue(BluetoothGattCharacteristic.FORMAT_UINT8, offset + 1);

		return (upperByte << 8) + lowerByte;
	}  
}
