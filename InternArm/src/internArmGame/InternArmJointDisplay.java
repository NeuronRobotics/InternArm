package internArmGame;
 
import com.jme3.app.SimpleApplication;
import com.jme3.asset.TextureKey;
import com.jme3.material.Material;
import com.jme3.scene.Geometry;
import com.jme3.scene.Mesh;
import com.jme3.scene.shape.Box;
import com.jme3.scene.shape.Sphere;
import com.jme3.math.ColorRGBA;
import com.jme3.math.FastMath;
import com.jme3.math.Matrix3f;
import com.jme3.input.KeyInput;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.AnalogListener;
import com.jme3.input.controls.KeyTrigger;
import com.jme3.math.Vector3f;
import com.jme3.scene.Node;
import com.jme3.math.Quaternion;
import com.jme3.system.Timer;
import com.jme3.texture.Texture;
import com.jme3.scene.debug.Arrow;

 
/** Sample 5 - how to map keys and mousebuttons to actions */
public class InternArmJointDisplay extends SimpleApplication{
 
  public static void main(String[] args) {
	  
	  InternArmJointDisplay app = new InternArmJointDisplay();
	  app.start(); 
  }
  
  protected Geometry Base;
  protected Geometry Joint1;
  protected Geometry Joint2;
  protected Geometry Joint3;
  protected Geometry Joint4;
  protected Geometry Joint5;
  protected Geometry Joint6;
  
  protected Node NBase;
  protected Node NJoint1;
  protected Node NJoint2;
  protected Node NJoint3;
  protected Node NJoint4;
  protected Node NJoint5;
  protected Node NJoint6;
  
  
  boolean isRunning = true;
 
  @Override
  public void simpleInitApp() {
	
    initArm();
   // initFloor();
    /** Configure cam to look at scene */
    cam.setLocation(new Vector3f(0, 4f, 16f));
    cam.lookAt(new Vector3f(2, 2, 0), Vector3f.UNIT_Y);
    flyCam.setMoveSpeed(50);
   
  }
  PosUpdate Updater = new PosUpdate();
  float x[];
  float y[];
  float z[];
  float rotations[][];
  Quaternion q1 = new Quaternion();
  Quaternion q2 = new Quaternion();
  Quaternion q3 = new Quaternion();
Quaternion q4 = new Quaternion();
Quaternion q5 = new Quaternion();
Quaternion q6 = new Quaternion();
  boolean go = true;
  Timer myTimer = getTimer();
  @Override
  public void simpleUpdate(float tpf) {
	  
	  	x = Updater.getXPos();
	  	y = Updater.getYPos();
		z = Updater.getZPos();
		rotations = Updater.getrotMatrix();
		
		q1.set(rotations[0][1],rotations[0][2],rotations[0][3],rotations[0][0]);
		q2.set(rotations[1][1],rotations[1][2],rotations[1][3],rotations[1][0]);
		q3.set(rotations[1][1],rotations[1][2],rotations[1][3],rotations[1][0]);
		q4.set(rotations[1][1],rotations[1][2],rotations[1][3],rotations[1][0]);
		q5.set(rotations[1][1],rotations[1][2],rotations[1][3],rotations[1][0]);
		q6.set(rotations[1][1],rotations[1][2],rotations[1][3],rotations[1][0]);
		NJoint1.setLocalTranslation((x[0]/10),(y[0]/10),(z[0]/10));		
		NJoint2.setLocalTranslation((x[1]/10),(y[1]/10),(z[1]/10));		
		NJoint3.setLocalTranslation((x[2]/10),(y[2]/10),(z[2]/10));		
		NJoint4.setLocalTranslation((x[3]/10),(y[3]/10),(z[3]/10));
		NJoint5.setLocalTranslation((x[4]/10),(y[4]/10),(z[4]/10));
		NJoint6.setLocalTranslation((x[5]/10),(y[5]/10),(z[5]/10));
		
		NJoint1.setLocalRotation(q1);
		NJoint2.setLocalRotation(q2);
		NJoint3.setLocalRotation(q3);
		NJoint4.setLocalRotation(q4);
		NJoint5.setLocalRotation(q5);
		NJoint6.setLocalRotation(q6);
}

  public void initFloor(){
    Box floor = new Box(10f, 0.1f, 5f);
    Geometry floor_geo = new Geometry("Floor", floor);
    Material floor_mat = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
    TextureKey key3 = new TextureKey("Textures/Terrain/Pond/Pond.jpg");
    key3.setGenerateMips(true);
    Texture tex3 = assetManager.loadTexture(key3);
    tex3.setWrap(Texture.WrapMode.Repeat);
    floor_mat.setTexture("ColorMap", tex3);
    floor_geo.setMaterial(floor_mat);
    floor_geo.setLocalTranslation(0, -0.1f, 0);
    rootNode.attachChild(floor_geo);
  }
  public void initArm(){
	NBase = new Node("pivot");
    Box base = new Box(.1f, .1f, .1f);
    Base = new Geometry("Base", base);
    Material mat = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
    mat.setColor("Color", ColorRGBA.Blue);
    Base.setMaterial(mat);
    rootNode.attachChild(NBase);
    NBase.attachChild(Base);

    
    NJoint1 = new Node("pivot");
    Sphere b1 = new Sphere(12,12,.5f);
    Joint1 = new Geometry("Joint1", b1);
    Material mat1 = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
    mat1.setColor("Color", ColorRGBA.Blue);
    Joint1.setMaterial(mat1);
    NBase.attachChild(NJoint1);
    NJoint1.attachChild(Joint1);
   
    NJoint2 = new Node("pivot");
    Sphere b2 = new Sphere(12,12,.5f);
    Joint2 = new Geometry("Joint2", b2);
    Material mat2 = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
    mat2.setColor("Color", ColorRGBA.Blue);
    Joint2.setMaterial(mat2);
    NBase.attachChild(NJoint2);
    NJoint2.attachChild(Joint2);
   
    NJoint3 = new Node("pivot");
    Sphere b3 = new Sphere(12,12,.5f);
    Joint3 = new Geometry("Joint3", b3);
    Material mat3 = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
    mat3.setColor("Color", ColorRGBA.Blue);
    Joint3.setMaterial(mat3);
    NBase.attachChild(NJoint3);
    NJoint3.attachChild(Joint3);
    
    NJoint4 = new Node("pivot");
    Sphere b4 = new Sphere(12,12,.5f);
    Joint4 = new Geometry("Joint4", b4);
    Material mat4 = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
    mat4.setColor("Color", ColorRGBA.Blue);
    Joint4.setMaterial(mat4);
    NBase.attachChild(NJoint4);
    NJoint4.attachChild(Joint4);
   
    NJoint5 = new Node("pivot");
    Sphere b5 = new Sphere(12,12,.5f);
    Joint5 = new Geometry("Joint5", b5);
    Material mat5 = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
    mat5.setColor("Color", ColorRGBA.Blue);
    Joint5.setMaterial(mat5);
    NBase.attachChild(NJoint5);
    NJoint5.attachChild(Joint5);
    
    NJoint6 = new Node("pivot");
    Sphere b6 = new Sphere(12,12,.5f);
    Joint6 = new Geometry("Joint6", b6);
    Material mat6 = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
    mat6.setColor("Color", ColorRGBA.Blue);
    Joint6.setMaterial(mat6);
    NBase.attachChild(NJoint6);
    NJoint6.attachChild(Joint6);
    ////Debugging
    Quaternion roll90 = new Quaternion(); 
    roll90.fromAngleAxis( -FastMath.PI/2 , new Vector3f(1,0,0) ); 
    NBase.setLocalRotation( roll90 );
//    roll90.fromAngleAxis( -FastMath.PI/2 , new Vector3f(0,1,0) ); 
//    NBase.setLocalRotation( roll90 );
//    roll90.fromAngleAxis( -FastMath.PI/2 , new Vector3f(0,0,1) ); 
//    NBase.setLocalRotation( roll90 );
    attachCoordinateAxes(new Vector3f(0f,0f,0f),rootNode);
    attachCoordinateAxes(new Vector3f(0f,0f,0f),NBase);
    attachCoordinateAxes(new Vector3f(0f,0f,0f),NJoint1);
    attachCoordinateAxes(new Vector3f(0f,0f,0f),NJoint2);
    attachCoordinateAxes(new Vector3f(0f,0f,0f),NJoint3);
    attachCoordinateAxes(new Vector3f(0f,0f,0f),NJoint4);
    attachCoordinateAxes(new Vector3f(0f,0f,0f),NJoint5);
    attachCoordinateAxes(new Vector3f(0f,0f,0f),NJoint6);
    
  }

private void attachCoordinateAxes(Vector3f pos, Node node) {
	  Arrow arrow = new Arrow(Vector3f.UNIT_X);
	  arrow.setLineWidth(12f); // make arrow thicker
	  putShape(arrow, ColorRGBA.Red, node).setLocalTranslation(pos);
	 
	  arrow = new Arrow(Vector3f.UNIT_Y);
	  arrow.setLineWidth(12f); // make arrow thicker
	  putShape(arrow, ColorRGBA.Green, node).setLocalTranslation(pos);
	 
	  arrow = new Arrow(Vector3f.UNIT_Z);
	  arrow.setLineWidth(12f); // make arrow thicker
	  putShape(arrow, ColorRGBA.Blue, node).setLocalTranslation(pos);
	}
	 
	private Geometry putShape(Mesh shape, ColorRGBA color, Node node){
	  Geometry g = new Geometry("coordinate axis", shape);
	  Material mat = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
	  mat.getAdditionalRenderState().setWireframe(true);
	  mat.setColor("Color", color);
	  g.setMaterial(mat);
	  node.attachChild(g);
	  return g;
	}
} 

          