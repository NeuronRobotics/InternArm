package internArmGame;

import java.util.ArrayList;

import com.neuronrobotics.sdk.addons.kinematics.AbstractKinematicsNR;
import com.neuronrobotics.sdk.addons.kinematics.DHParameterKinematics;
import com.neuronrobotics.sdk.addons.kinematics.IJointSpaceUpdateListenerNR;
import com.neuronrobotics.sdk.addons.kinematics.ITaskSpaceUpdateListenerNR;
import com.neuronrobotics.sdk.addons.kinematics.JointLimit;
import com.neuronrobotics.sdk.addons.kinematics.math.TransformNR;
import com.neuronrobotics.sdk.dyio.DyIO;
import com.neuronrobotics.sdk.dyio.DyIOChannel;
import com.neuronrobotics.sdk.ui.ConnectionDialog;

public class PosUpdate implements ITaskSpaceUpdateListenerNR, IJointSpaceUpdateListenerNR {
	
	private DyIO Master;
	private DHParameterKinematics model;
//	private ArrayList<Float> xArray =new ArrayList<Float>();
//	private ArrayList<Float> yArray =new ArrayList<Float>();
//	private ArrayList<Float> zArray =new ArrayList<Float>();
	private float[] X = new float[6];
	private float[] Y = new float[6] ;
	private float[] Z = new float[6];
	public PosUpdate(){
		Master = new DyIO();
		DyIO.disableFWCheck();
		if (!ConnectionDialog.getBowlerDevice(Master)){
			System.exit(2);
		}
		for (DyIOChannel c: Master.getChannels()){
			c.setAsync(false);
		}
		model = new DHParameterKinematics(Master,"TrobotMaster.xml");
		model.addPoseUpdateListener(this);
		model.addJointSpaceListener(this);

	}
	
	 
	public static void main(String[] args) {
		
		new PosUpdate();
		
	}
	

	public void onTargetTaskSpaceUpdate(AbstractKinematicsNR arg0,TransformNR arg1) {
		// TODO Auto-generated method stub
		
	}


	public void onTaskSpaceUpdate(AbstractKinematicsNR arg0, TransformNR arg1) {
		// TODO Auto-generated method stub
		//System.out.println("New transform: " + arg1);
		System.out.println("Chain ");
		int i=0;
		for(TransformNR nr:model.getChainTransformations()){
				float tempx = (float)nr.getX();
				float tempy = (float)nr.getY();
				float tempz = (float)nr.getZ();
				X[i] = tempx;
				Y[i] = tempy;
				Z[i] = tempz;
//				float that = (float) nr.getX();
//				float that2 = (float)nr.getY();
//				float that3 = (float)nr.getZ();
//			System.out.println(" link # "+(i)+" \n"+nr.getRotation()+" \n"+that+" \n"+that2+" \n"+that3);
			System.out.println(" link # "+(i)+" \n"+nr.getRotation()+" \n"+X[i]+" \n"+Y[i]+" \n"+Z[i]);
			i++;
			
		}
	}


	public void onJointSpaceUpdate(AbstractKinematicsNR source, double[] joints) {
		// TODO Auto-generated method stub
		
		
	}


	public void onJointSpaceTargetUpdate(AbstractKinematicsNR source,
			double[] joints) {
		// TODO Auto-generated method stub
		
	}


	public void onJointSpaceLimit(AbstractKinematicsNR source, int axis,JointLimit event) {
		// TODO Auto-generated method stub
		
	}
	
	public float[] getXPos(){
		
		return X;
	}
	public float[] getYPos(){
		
		return Y;
	}
	public float[] getZPos(){
	
		return Z;
}
}