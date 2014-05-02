package internArmGame;
 
import com.jme3.app.SimpleApplication;
import com.jme3.asset.TextureKey;
import com.jme3.material.Material;
import com.jme3.scene.Geometry;
import com.jme3.scene.shape.Box;
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
public class InternArmDisplay extends SimpleApplication{
 
  public static void main(String[] args) {
	  InternArmDisplay app = new InternArmDisplay();
	  app.start();
  }
  protected Geometry Shoulder;
  protected Geometry UpperArm;
  protected Geometry ForeArm;
  protected Geometry Wrist;
  protected Geometry GripLeft;
  protected Geometry GripRight;
  protected Geometry GripBase;
  protected Node NLiftShoulder;
  protected Node NRotateShoulder;
  protected Node NBendElbow;
  protected Node NWrist;
  protected Node NGripLeft;
  protected Node NGripRight;
  protected Geometry PhyElbow;
  protected Geometry PhyWrist;
  boolean isRunning = true;
 
  @Override
  public void simpleInitApp() {
    initArm();
    initArmPos();
    initFloor();
    initKeys(); // load my custom keybinding
    /** Configure cam to look at scene */
    cam.setLocation(new Vector3f(0, 4f, 16f));
    cam.lookAt(new Vector3f(2, 2, 0), Vector3f.UNIT_Y);
    flyCam.setMoveSpeed(50);
   
  }
  
  float x[];
  float y[];
  float z[];
  Quaternion q1 = new Quaternion();
  Quaternion q2 = new Quaternion();
  boolean go = true;
  Timer myTimer = getTimer();
  @Override
  public void simpleUpdate(float tpf) {
	  
//	  	z = (PosUpdate.getXPos());
//	  	x = (PosUpdate.getYPos());
//		y = (PosUpdate.getZPos());
//	    NBendElbow.setLocalTranslation(x[2],y[2],z[2]);
}

 
  /** Custom Keybinding: Map named actions to inputs. */
  private void initKeys() {
    // You can map one or several inputs to one named action
    inputManager.addMapping("Pause",  new KeyTrigger(KeyInput.KEY_P));
    
    inputManager.addMapping("RotateShoulderLeft",   new KeyTrigger(KeyInput.KEY_H));
    inputManager.addMapping("RotateShoulderRight",   new KeyTrigger(KeyInput.KEY_J));
    
    inputManager.addMapping("LiftShoulder",  new KeyTrigger(KeyInput.KEY_I));
    inputManager.addMapping("LowerShoulder", new KeyTrigger(KeyInput.KEY_K));
    
    inputManager.addMapping("BendElbow",  new KeyTrigger(KeyInput.KEY_O));
    inputManager.addMapping("ExtendElbow", new KeyTrigger(KeyInput.KEY_L));
    
    inputManager.addMapping("WristLeft",  new KeyTrigger(KeyInput.KEY_Y));
    inputManager.addMapping("WristRight", new KeyTrigger(KeyInput.KEY_U));
    
    inputManager.addMapping("Pinch",  new KeyTrigger(KeyInput.KEY_N));
    inputManager.addMapping("Release", new KeyTrigger(KeyInput.KEY_M));
    
    // Add the names to the action listener.
    inputManager.addListener(actionListener,"Pause");
    inputManager.addListener(analogListener,"RotateShoulderLeft", "RotateShoulderRight", "LiftShoulder","LowerShoulder",
            "BendElbow","ExtendElbow","WristLeft","WristRight","Pinch","Release");
 
  }
 
  private ActionListener actionListener = new ActionListener() {
    public void onAction(String name, boolean keyPressed, float tpf) {
      if (name.equals("Pause") && !keyPressed) {
        isRunning = !isRunning;
      }
    }
  };
 
  private AnalogListener analogListener = new AnalogListener() {
    public void onAnalog(String name, float value, float tpf) {
      if (isRunning) {
        if (name.equals("RotateShoulderLeft")) {
          NRotateShoulder.rotate(0, -value*speed, 0);
        }
        if (name.equals("RotateShoulderRight")) {
          NRotateShoulder.rotate(0, value*speed, 0);
        }
        if (name.equals("LiftShoulder")) {
          NLiftShoulder.rotate(0, 0, value*speed);
        }
        if (name.equals("LowerShoulder")) {
          NLiftShoulder.rotate(0, 0, -value*speed);
        }
        if (name.equals("BendElbow")) {
          NBendElbow.rotate(0, 0, value*speed);
        }
        if (name.equals("ExtendElbow")) {
          NBendElbow.rotate(0, 0, -value*speed);
        }
        if (name.equals("WristRight")) {
          NWrist.rotate(0, value*speed, 0);
        }
        if (name.equals("WristLeft")) {
          NWrist.rotate(0, -value*speed, 0);
        }
        if (name.equals("Pinch")) {
          Vector3f vR = NGripRight.getLocalTranslation();
          Vector3f vL = NGripLeft.getLocalTranslation();
          NGripLeft.setLocalTranslation(vL.x,vL.y,vL.z-(0.001f));
          NGripRight.setLocalTranslation(vR.x,vR.y,vR.z+(0.001f));
        }
        if (name.equals("Release")) {
          Vector3f vR = NGripRight.getLocalTranslation();
          Vector3f vL = NGripLeft.getLocalTranslation();
          NGripLeft.setLocalTranslation(vL.x,vL.y,vL.z+(0.001f));
          NGripRight.setLocalTranslation(vR.x,vR.y,vR.z-(0.001f));
        }
      } 
        else {
        System.out.println("Press P to unpause.");
      }
    }
  };
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
    Shoulder = new Geometry("Joint1", base);
    Material mat = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
    mat.setColor("Color", ColorRGBA.Blue);
    Shoulder.setMaterial(mat);
    NRotateShoulder = new Node("pivot");
    
    Box b2 = new Box(0.5f, 2f, 0.5f);
    UpperArm = new Geometry("Joint2", b2);
    Material mat2 = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
    mat2.setColor("Color", ColorRGBA.Red);
    UpperArm.setMaterial(mat2);
    NLiftShoulder = new Node("pivot");   
    
    Box b3 = new Box(0.5f, 2f, 0.5f);
    ForeArm = new Geometry("Joint3", b3);
    Material mat3 = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
    mat3.setColor("Color", ColorRGBA.Green);
    ForeArm.setMaterial(mat3);
    NBendElbow = new Node("pivot");
    
    Box b4 = new Box(0.25f, 2.5f, 0.25f);
    Wrist = new Geometry("Joint3", b4);
    Material mat4 = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
    mat4.setColor("Color", ColorRGBA.Orange);
    Wrist.setMaterial(mat4);
    NWrist = new Node("pivot");
    
    Box b5 = new Box(0.5f, 0.5f, 0.25f);
    GripLeft = new Geometry("Joint3", b5);
    Material mat5 = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
    mat5.setColor("Color", ColorRGBA.Blue);
    GripLeft.setMaterial(mat5);
    NGripLeft= new Node("pivot");
    
    Box b6 = new Box(0.5f, 0.5f, 0.25f);
    GripRight = new Geometry("Joint3", b6);
    Material mat6 = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
    mat6.setColor("Color", ColorRGBA.Blue);
    GripRight.setMaterial(mat6);
    NGripRight = new Node("pivot");
    
    Box b7 = new Box(0.5f, 0.01f, 1.0f);
    GripBase = new Geometry("Joint3", b7);
    Material mat7 = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
    mat7.setColor("Color", ColorRGBA.Blue);
    GripBase.setMaterial(mat7);
  
    rootNode.attachChild(NRotateShoulder);
    NRotateShoulder.attachChild(Shoulder);
    NRotateShoulder.attachChild(NLiftShoulder);
    NLiftShoulder.attachChild(UpperArm);
    rootNode.attachChild(NBendElbow);
    NBendElbow.attachChild(ForeArm);
    NBendElbow.attachChild(NWrist);
    NWrist.attachChild(Wrist);
    NWrist.attachChild(NGripLeft);
    NWrist.attachChild(NGripRight);
    NWrist.attachChild(GripBase);
    NGripLeft.attachChild(GripLeft);
    NGripRight.attachChild(GripRight);
    
    
  } 
  public void initArmPos(){
	  
	Shoulder.setLocalTranslation(0.0f, 0.0f, 0.0f);
    NRotateShoulder.setLocalTranslation(0.0f, 1.0f, 0.0f);
    
    NLiftShoulder.setLocalTranslation(0.0f, 0.25f, 1.5f);
    UpperArm.setLocalTranslation(0.0f, 1.25f, 0.0f);
    
    NBendElbow.setLocalTranslation(0.0f, 2.5f, 1.0f);
    ForeArm.setLocalTranslation(0.0f, 1.25f, 0.0f);
    
    NWrist.setLocalTranslation(0.0f, 0.0f, 0.0f);
    Wrist.setLocalTranslation(0.0f, 2.0f, 0.0f);
    
    GripBase.setLocalTranslation(0.0f,4.5f,0.0f);
    
    NGripLeft.setLocalTranslation(0.0f, 5.01f, 0.75f);
    GripLeft.setLocalTranslation(0.0f, 0.0f, 0.0f);
    
    NGripRight.setLocalTranslation(0.0f, 5.01f, -0.75f);
    GripRight.setLocalTranslation(0.0f, 0.0f, 0.0f); 
    
  }
}
          