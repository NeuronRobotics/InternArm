package internArmGame;

import com.jme3.material.Material;
import com.jme3.asset.AssetManager;
import com.jme3.material.RenderState.BlendMode;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector2f;
import com.jme3.math.Vector3f;
import com.jme3.renderer.queue.RenderQueue.Bucket;
import com.jme3.scene.Geometry;
import com.jme3.scene.Mesh;
import com.jme3.scene.Node;
import com.jme3.scene.VertexBuffer;
import com.jme3.scene.shape.Box;
import com.jme3.util.BufferUtils;

public class DrawGrid {
	
	private Node NGrid;
    private Geometry invisBrick;
    private Geometry visBrick;
    
    private Material material;
//    private int gridDepth = 26;
//    private int gridHeight = 20;
//    private int gridWidth = 19;
    
    private  float brickDepth = .05f;
    private  float brickWidth  = .05f;
    private  float brickHeight = .05f;
    
    private float startWidth = 0 - (2.7f-brickWidth);//Start 13 Bricks Left of 0 point (brickWidth * 26)
    private  float startHeight = 0 - (1.3f-brickWidth);//Start 6 Bricks below 0 point (brickHeight * 12)
    private  float startDepth = 0 - (2.3f-brickWidth);//Start 11 Bricks Behind 0 point (brickDepth * 22)
    private  float height = startHeight;
    private  float depth = startDepth;
    
    private float GridWidth = 3.8f;
    private float GridHeight = 4.0f;
    private float GridDepth = 5.3f;
    
    private int gridDepth = (int)(GridDepth/(2*brickDepth));
    private int gridHeight = (int)(GridHeight/(2*brickHeight));
    private int gridWidth = (int)(GridWidth/(2*brickWidth));
//    private Geometry[][][] GridBrick = new Geometry[gridDepth][gridHeight][gridWidth];
    private Geometry GridBrick;
    private  Box    box = new Box(brickWidth, brickHeight, brickDepth);
    private  ColorRGBA visible = new ColorRGBA(1,0,0,0.7f);
    private  ColorRGBA invisible = new ColorRGBA(0,0,0,0);
  
    public DrawGrid(Material mat)
    {
        invisBrick = new Geometry("brick",singleSide());
        material = mat;
        material.setColor("Color", invisible);
	    material.getAdditionalRenderState().setBlendMode(BlendMode.Alpha);;
	    invisBrick.setQueueBucket(Bucket.Transparent);
	    invisBrick.setMaterial(material);
	    
	    visBrick = VisibleBrick();
        NGrid = new Node("Grid");
        
        for(int a = 0; a < gridDepth; a++)
        {
            for(int b = 0; b < gridHeight; b++)
            {
                for(int c = 0; c < gridWidth; c++)
                {
//                    GridBrick[a][b][c] = invisBrick.clone();
                	GridBrick = invisBrick.clone();
                    Vector3f vt =
                    		new Vector3f(c * brickWidth * 2 + startWidth, brickHeight + height, depth);

//                    GridBrick[a][b][c].setLocalTranslation(vt);
//                    NGrid.attachChild(GridBrick[a][b][c]);
                    GridBrick.setLocalTranslation(vt);
                    NGrid.attachChild(GridBrick);
                }
                height += 2 * brickHeight;
            }
            height = startHeight;
		    depth += 2* brickDepth;
        }
    }
 
    private Mesh singleSide()
    {
        Mesh Quad = new Mesh();
        Vector3f [] vertices = new Vector3f[3];
        int [] indexes = new int[3];
 
        vertices[0] = new Vector3f(-.05f,-.05f,0f);
        vertices[1] = new Vector3f(0.05f,-.05f,0f);
        vertices[2] = new Vector3f(-.05f,0.05f,0f);
       // vertices[3] = new Vector3f(0.1f,0.1f,0f);
        
        Vector2f[] texCoord = new Vector2f[3];
        texCoord[0] = new Vector2f(0,0);
        texCoord[1] = new Vector2f(1,0);
        texCoord[2] = new Vector2f(0,1);
        //texCoord[3] = new Vector2f(1,1);
 
        indexes[0] = 2;
        indexes[1] = 0;
        indexes[2] = 1;
 
       // indexes[3] = 1;
       // indexes[4] = 3;
        //indexes[5] = 2;
// 
//        indexes[6] = 1;
//        indexes[7] = 0;
//        indexes[8] = 3;
// 
//        indexes[9] = 0;
//        indexes[10] = 2;
//        indexes[11] = 3;
 
        // Setting buffers
        Quad.setBuffer(VertexBuffer.Type.Position, 3, BufferUtils.createFloatBuffer(vertices));
        Quad.setBuffer(VertexBuffer.Type.TexCoord, 2, BufferUtils.createFloatBuffer(texCoord));
        Quad.setBuffer(VertexBuffer.Type.Index, 1, BufferUtils.createIntBuffer(indexes));
        Quad.updateBound();
 
        return Quad;
 
        /*Box s = new Box(0.1f,0.1f,0.1f);
        Geometry geom = new Geometry(“Ball”,s);
        geom.setMaterial(materials.Off());
        //geom.setQueueBucket(RenderQueue.Bucket.Translucent);
        //geom.setQueueBucket(RenderQueue.Bucket.Transparent); 
 
        return geom;*/
    }
    private Geometry VisibleBrick()
    	{
    		
	    	Geometry VisibleBrick = new Geometry("brick", box);
	    	Material wall_mat = material.clone();
		    wall_mat.setColor("Color", visible);
		    wall_mat.getAdditionalRenderState().setBlendMode(BlendMode.Alpha);;
		    VisibleBrick.setQueueBucket(Bucket.Transparent);
		    VisibleBrick.setMaterial(wall_mat);
		    return VisibleBrick;
    	}
 
    public Geometry ShowBrick()
    {
	    return visBrick.clone();
    }
 
    // not used
    public Geometry HideBrick()
    {
	    return invisBrick.clone();
    }
    public Node getAll()
    {
        return NGrid;
    }
 
    // not used (detaching cube node instead) when changing dimension in-game
    public void detachChildren()
    {
        NGrid.detachAllChildren();
    }
}
