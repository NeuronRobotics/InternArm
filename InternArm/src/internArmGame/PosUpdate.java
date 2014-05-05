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
	private float[] X = new float[6];
	private float[] Y = new float[6] ;
	private float[] Z = new float[6];
	private float[][] rotMatrix = new float[6][4];
	
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
		System.out.println("Chain ");
		int i=0;
		for(TransformNR nr:model.getChainTransformations()){
				X[i]  = (float)nr.getX();
				Y[i] = (float)nr.getY();
				Z[i] = (float)nr.getZ();
				rotMatrix[i][0]= (float)nr.getRotation().getRotationMatrix2QuaturnionW();
				rotMatrix[i][1]= (float)nr.getRotation().getRotationMatrix2QuaturnionX();
				rotMatrix[i][2]= (float)nr.getRotation().getRotationMatrix2QuaturnionY();
				rotMatrix[i][3]= (float)nr.getRotation().getRotationMatrix2QuaturnionZ();

			System.out.println(" link # "+(i)+" "
					+ "\nRotation: w="+rotMatrix[i][0]+" \t x="+rotMatrix[i][1]+" \t y="+rotMatrix[i][2]+" \t z="+rotMatrix[i][3]+" \n Position : X="
					+X[i]+" \n Y = "+Y[i]+" \n Z = "+Z[i]);
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
	public float[][] getrotMatrix(){
		return rotMatrix;
	}
}