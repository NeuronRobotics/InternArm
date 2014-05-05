package internArmGame;
 
import com.jme3.app.SimpleApplication;
import com.jme3.collision.CollisionResults;
import com.jme3.material.Material;
import com.jme3.material.RenderState.BlendMode;
import com.jme3.renderer.queue.RenderQueue.Bucket;
import com.jme3.scene.Geometry;
import com.jme3.scene.shape.Box;
import com.jme3.scene.shape.Sphere;
import com.jme3.math.ColorRGBA;
import com.jme3.math.FastMath;
import com.jme3.math.Ray;
import com.jme3.input.KeyInput;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.KeyTrigger;
import com.jme3.math.Vector3f;
import com.jme3.scene.Node;
import com.jme3.math.Quaternion;


 
/** Sample 5 - how to map keys and mousebuttons to actions */
public class HighlightGrid extends SimpleApplication{
 
  public static void main(String[] args) {
	  
	  HighlightGrid app = new HighlightGrid();
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
  
  //Grid size definition
  private Node NGrid;
  
  private static final float brickLength = .1f;
  private static final float brickWidth  = .1f;
  private static final float brickHeight = .1f;
  private static final Box    box;
  private static final ColorRGBA visable;
  private static final ColorRGBA invisable;
  static{
	  box = new Box(brickLength, brickHeight, brickWidth);
	  visable = new ColorRGBA(1,0,0,1.0f);
	  invisable = new ColorRGBA(1,0,0,0.1f);
  }
  
  @Override
  public void simpleInitApp() {
	NGrid = new Node("Shootables");
	rootNode.attachChild(NGrid);
	isRunning = false;
    initArm();
    initKeys();
    initGrid();
    /** Configure cam to look at scene */
    cam.setLocation(new Vector3f(0, 4f, 16f));
    cam.lookAt(new Vector3f(2, 2, 0), Vector3f.UNIT_Y);
    flyCam.setMoveSpeed(10);
 
  }
  PosUpdate Updater = new PosUpdate();
  private float x[];
  private float y[];
  private float z[];
  private float rotations[][];
  
  Quaternion q1 = new Quaternion();
  Quaternion q2 = new Quaternion();
  Quaternion q3 = new Quaternion();
  Quaternion q4 = new Quaternion();
  Quaternion q5 = new Quaternion();
  Quaternion q6 = new Quaternion();

  @Override
  public void simpleUpdate(float tpf) { 
	  	x = Updater.getXPos();
	  	y = Updater.getYPos();
		z = Updater.getZPos();
		rotations = Updater.getrotMatrix();
		
//		q1.set(rotations[0][1],rotations[0][2],rotations[0][3],rotations[0][0]);
//		q2.set(rotations[1][1],rotations[1][2],rotations[1][3],rotations[1][0]);
//		q3.set(rotations[2][1],rotations[2][2],rotations[2][3],rotations[2][0]);
//		q4.set(rotations[3][1],rotations[3][2],rotations[3][3],rotations[3][0]);
//		q5.set(rotations[4][1],rotations[4][2],rotations[4][3],rotations[4][0]);
		q6.set(rotations[5][1],rotations[5][2],rotations[5][3],rotations[5][0]);
		
//		NJoint1.setLocalTranslation((x[0]/10),(y[0]/10),(z[0]/10));		
//		NJoint2.setLocalTranslation((x[1]/10),(y[1]/10),(z[1]/10));		
//		NJoint3.setLocalTranslation((x[2]/10),(y[2]/10),(z[2]/10));		
//		NJoint4.setLocalTranslation((x[3]/10),(y[3]/10),(z[3]/10));
//		NJoint5.setLocalTranslation((x[4]/10),(y[4]/10),(z[4]/10));
		NJoint6.setLocalTranslation((x[5]/100),(y[5]/100),(z[5]/100));
		
//		NJoint1.setLocalRotation(q1);
//		NJoint2.setLocalRotation(q2);
//		NJoint3.setLocalRotation(q3);
//		NJoint4.setLocalRotation(q4);
//		NJoint5.setLocalRotation(q5);
		NJoint6.setLocalRotation(q6);
		if(isRunning){
			CollisionResults results = new CollisionResults();
			Ray ray = new Ray(NJoint6.getWorldTranslation(), Vector3f.UNIT_Y);
	        NGrid.collideWith(ray, results);
	        if(results.size() > 0)
	        {
		        Geometry geom = results.getClosestCollision().getGeometry();
		        Material mat = geom.getMaterial();
		        mat.setColor("Color", visable);
		        geom.setMaterial(mat);
	        }
		}
}
  private void initKeys() {
	    inputManager.addMapping("Draw",  new KeyTrigger(KeyInput.KEY_P));
	    inputManager.addListener(actionListener,"Draw");
  }
  
  private ActionListener actionListener = new ActionListener() {
	    public void onAction(String name, boolean keyPressed, float tpf) {
	      if (name.equals("Draw") && !keyPressed) {
	        isRunning = !isRunning;
	      }
	    }
	  };
  public void initGrid(){
	  
	  float startpt = 0 - (brickLength * 20);
	    float height = 0 - (brickHeight * 6);
	    float width = 0 - (brickWidth * 16);
	    for (int k = 0; k <20; k++){
		    for (int j = 0; j < 16; j++) {
		      for (int i = 0; i < 26; i++) {
		        Vector3f vt =
		         new Vector3f(i * brickLength * 2 + startpt, brickHeight + height, width);
		        makeBrick(vt);
		      }
		      height += 2 * brickHeight;  
		    }
		    height = 0 - (brickHeight * 3);;
		    width += 2* brickWidth;
	    }
  }
  public void initArm(){
	Box base = new Box(.1f, .1f, .1f); 
	Sphere b1 = new Sphere(3,3,.1f);
	Material mat = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
	mat.setColor("Color", ColorRGBA.Blue);
	
	NBase = new Node("pivot");
    Base = new Geometry("Base", base);
    Base.setMaterial(mat);
    rootNode.attachChild(NBase);
    NBase.attachChild(Base);
//
//    NJoint1 = new Node("pivot");
//    Joint1 = new Geometry("Joint1", b1);
//    Joint1.setMaterial(mat);
//    NBase.attachChild(NJoint1);
//    NJoint1.attachChild(Joint1);
//   
//    NJoint2 = new Node("pivot");
//    Joint2 = new Geometry("Joint2", b1);
//    Joint2.setMaterial(mat);
//    NBase.attachChild(NJoint2);
//    NJoint2.attachChild(Joint2);
//   
//    NJoint3 = new Node("pivot");
//    Joint3 = new Geometry("Joint3", b1);
//    Joint3.setMaterial(mat);
//    NBase.attachChild(NJoint3);
//    NJoint3.attachChild(Joint3);
//    
//    NJoint4 = new Node("pivot");
//    Joint4 = new Geometry("Joint4", b1);
//    Joint4.setMaterial(mat);
//    NBase.attachChild(NJoint4);
//    NJoint4.attachChild(Joint4);
//   
//    NJoint5 = new Node("pivot");
//    Joint5 = new Geometry("Joint5", b1);
//    Joint5.setMaterial(mat);
//    NBase.attachChild(NJoint5);
//    NJoint5.attachChild(Joint5);
//    
    NJoint6 = new Node("pivot");
    Joint6 = new Geometry("Joint6", b1);
    Joint6.setMaterial(mat);
    NBase.attachChild(NJoint6);
    NJoint6.attachChild(Joint6);

    Quaternion roll90 = new Quaternion(); 
    roll90.fromAngleAxis( -FastMath.PI/2 , new Vector3f(1,0,0) ); 
    NBase.setLocalRotation( roll90 );   
  }
 
  public void makeBrick(Vector3f loc) {
	    /** Create a brick geometry and attach to scene graph. */
	    Geometry brick_geo = new Geometry("brick", box);
	    Material wall_mat = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
	    wall_mat.setColor("Color", invisable);
	    wall_mat.getAdditionalRenderState().setBlendMode(BlendMode.Alpha);;
	    brick_geo.setQueueBucket(Bucket.Transparent);
	    brick_geo.setMaterial(wall_mat);
	    NGrid.attachChild(brick_geo);
	    /** Position the brick geometry  */
	    brick_geo.setLocalTranslation(loc);
	  }

} 

          