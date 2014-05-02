package internArmGame;
 
import com.jme3.app.SimpleApplication;
import com.jme3.asset.TextureKey;
import com.jme3.material.Material;
import com.jme3.scene.Geometry;
import com.jme3.scene.shape.Box;
import com.jme3.scene.shape.Sphere;
import com.jme3.math.ColorRGBA;
import com.jme3.input.KeyInput;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.AnalogListener;
import com.jme3.input.controls.KeyTrigger;
import com.jme3.math.Vector3f;
import com.jme3.scene.Node;
import com.jme3.math.Quaternion;
import com.jme3.system.Timer;
import com.jme3.texture.Texture;

 
/** Sample 5 - how to map keys and mousebuttons to actions */
public class InternArmJointDisplay extends SimpleApplication{
 
  public static void main(String[] args) {
	  
	  InternArmJointDisplay app = new InternArmJointDisplay();
	  app.start(); 
  }
  
  protected Geometry Base;
  protected Geometry Shoulder;
  protected Geometry Elbow;
  protected Geometry Wrist;
  protected Node NShoulder;
  protected Node NElbow;
  protected Node NWrist;
  
  protected Geometry Joint1;
  protected Geometry Joint2;
  protected Geometry Joint3;
  protected Geometry Joint4;
  protected Geometry Joint5;
  protected Geometry Joint6;
  
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
    initFloor();
    /** Configure cam to look at scene */
    cam.setLocation(new Vector3f(0, 4f, 16f));
    cam.lookAt(new Vector3f(2, 2, 0), Vector3f.UNIT_Y);
    flyCam.setMoveSpeed(50);
   
  }
  PosUpdate Updater = new PosUpdate();
  float x[];
  float y[];
  float z[];
  Quaternion q1 = new Quaternion();
  Quaternion q2 = new Quaternion();
  boolean go = true;
  Timer myTimer = getTimer();
  @Override
  public void simpleUpdate(float tpf) {
	  
	  	x = (Updater.getXPos());
	  	z = (Updater.getYPos());
		y = (Updater.getZPos());
		NJoint1.setLocalTranslation((x[0]/10),(y[0]/10),(z[0]/10));
		NJoint2.setLocalTranslation((x[1]/10),(y[1]/10),(z[1]/10));
		NJoint3.setLocalTranslation((x[2]/10),(y[2]/10),(z[2]/10));
		NJoint4.setLocalTranslation((x[3]/10),(y[3]/10),(z[3]/10));
		NJoint5.setLocalTranslation((x[4]/10),(y[4]/10),(z[4]/10));
		NJoint6.setLocalTranslation((x[5]/10),(y[5]/10),(z[5]/10));
		
//		NShoulder.setLocalTranslation(x[1],y[1],z[1]);
//		NElbow.setLocalTranslation(x[2],y[2],z[2]);
//		NWrist.setLocalTranslation(x[5],y[5],z[5]);

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
    Box base = new Box(1, 1, 1);
    Base = new Geometry("Base", base);
    Material mat = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
    mat.setColor("Color", ColorRGBA.Blue);
    Base.setMaterial(mat);
    rootNode.attachChild(Base);
    Base.setLocalTranslation(0f,1f,0f);
    
    NJoint1 = new Node("pivot");
    Sphere b1 = new Sphere(64,64,.5f);
    Joint1 = new Geometry("Joint1", b1);
    Material mat1 = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
    mat1.setColor("Color", ColorRGBA.Blue);
    Joint1.setMaterial(mat1);
    rootNode.attachChild(NJoint1);
    NJoint1.attachChild(Joint1);
   
    NJoint2 = new Node("pivot");
    Sphere b2 = new Sphere(64,64,.5f);
    Joint2 = new Geometry("Joint2", b2);
    Material mat2 = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
    mat2.setColor("Color", ColorRGBA.Blue);
    Joint2.setMaterial(mat2);
    NJoint1.attachChild(NJoint2);
    NJoint2.attachChild(Joint2);
   
    NJoint3 = new Node("pivot");
    Sphere b3 = new Sphere(64,64,.5f);
    Joint3 = new Geometry("Joint3", b3);
    Material mat3 = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
    mat3.setColor("Color", ColorRGBA.Blue);
    Joint3.setMaterial(mat3);
    NJoint2.attachChild(NJoint3);
    NJoint3.attachChild(Joint3);
    
    NJoint4 = new Node("pivot");
    Sphere b4 = new Sphere(64,64,.5f);
    Joint4 = new Geometry("Joint4", b4);
    Material mat4 = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
    mat4.setColor("Color", ColorRGBA.Blue);
    Joint4.setMaterial(mat4);
    NJoint3.attachChild(NJoint4);
    NJoint4.attachChild(Joint4);
   
    NJoint5 = new Node("pivot");
    Sphere b5 = new Sphere(64,64,.5f);
    Joint5 = new Geometry("Joint5", b5);
    Material mat5 = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
    mat5.setColor("Color", ColorRGBA.Blue);
    Joint5.setMaterial(mat5);
    NJoint4.attachChild(NJoint5);
    NJoint5.attachChild(Joint5);
    
    NJoint6 = new Node("pivot");
    Sphere b6 = new Sphere(64,64,.5f);
    Joint6 = new Geometry("Joint6", b6);
    Material mat6 = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
    mat6.setColor("Color", ColorRGBA.Blue);
    Joint6.setMaterial(mat6);
    NJoint5.attachChild(NJoint6);
    NJoint6.attachChild(Joint6);
    
//    NShoulder = new Node("pivot");
//    Sphere b1 = new Sphere(32,32,.5f);
//    Shoulder = new Geometry("Joint2", b1);
//    Material mat1 = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
//    mat1.setColor("Color", ColorRGBA.Blue);
//    Shoulder.setMaterial(mat1);
//    rootNode.attachChild(NShoulder);
//    NShoulder.attachChild(Shoulder);
//    NShoulder.setLocalTranslation(0f,5f,0f);
//
//    NElbow = new Node("pivot");
//    Sphere b2 = new Sphere(32,32,.5f);
//    Elbow = new Geometry("Joint2", b2);
//    Material mat2 = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
//    mat2.setColor("Color", ColorRGBA.Red);
//    Elbow.setMaterial(mat2);
//    rootNode.attachChild(NElbow);
//    NElbow.attachChild(Elbow);
//    NElbow.setLocalTranslation(0f,10f,0f);
//    
//    NWrist = new Node("pivot");   
//    Sphere b3 = new Sphere(32,32,.5f);
//    Wrist = new Geometry("Joint3", b3);
//    Material mat3 = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
//    mat3.setColor("Color", ColorRGBA.Green);
//    Wrist.setMaterial(mat3);
//    rootNode.attachChild(NWrist);
//    NWrist.attachChild(Wrist); 
//    NWrist.setLocalTranslation(0f,15f,0f);
  } 
}
          