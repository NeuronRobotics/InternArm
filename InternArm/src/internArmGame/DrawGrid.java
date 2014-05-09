package internArmGame;

import com.jme3.material.Material;
import com.jme3.math.Vector2f;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Mesh;
import com.jme3.scene.Node;
import com.jme3.scene.VertexBuffer;
import com.jme3.util.BufferUtils;

public class DrawGrid {
	
	private Node NGrid;
    private Geometry Brick;
    private Geometry Grid[][][];
    private Material material;
    private int gridDepth = 26;
    private int gridHeight = 20;
    private int gridWidth = 19;
    private int d;
    
    private static final float brickDepth = .1f;
    private static final float brickWidth  = .1f;
    private static final float brickHeight = .1f;
    private float startWidth = 0 - 2.6f;//Start 13 Bricks Left of 0 point (brickWidth * 26)
    private  float startHeight = 0 - 1.2f;//Start 6 Bricks below 0 point (brickHeight * 12)
    private  float startDepth = 0 - 2.2f;//Start 11 Bricks Behind 0 point (brickDepth * 22)
    private  float height = startHeight;
    private  float depth = startDepth;
 
    public DrawGrid()
    {
    	
        d = 1;
        Brick = new Geometry("brick",singleSide());
        material = new Material();
        NGrid = new Node("Grid");
        Brick.setMaterial(material);
 
        for(int a = 0; a < gridDepth; a++)
        {
            for(int b = 0; b < gridHeight; b++)
            {
                for(int c = 0; c < gridWidth; c++)
                {
                    Grid[a][b][c] = Brick.clone();
                    Vector3f vt =
                    		new Vector3f(c * brickWidth * 2 + startWidth, brickHeight + height, depth);

                    Grid[a][b][c].setLocalTranslation(vt);
                    NGrid.attachChild(Grid[a][b][c]);
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
        Vector3f [] vertices = new Vector3f[4];
        int [] indexes = new int[12];
 
        vertices[0] = new Vector3f(0,0,0);
        vertices[1] = new Vector3f(0.1f,0f,0f);
        vertices[2] = new Vector3f(0f,0.1f,0);
        vertices[3] = new Vector3f(0.1f,0.1f,0);
        
        Vector2f[] texCoord = new Vector2f[4];
        texCoord[0] = new Vector2f(0,0);
        texCoord[1] = new Vector2f(1,0);
        texCoord[2] = new Vector2f(0,1);
        texCoord[3] = new Vector2f(1,1);
 
        indexes[0] = 2;
        indexes[1] = 0;
        indexes[2] = 1;
 
        indexes[3] = 1;
        indexes[4] = 3;
        indexes[5] = 2;
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
        Quad.setBuffer(VertexBuffer.Type.Index, 3, BufferUtils.createIntBuffer(indexes));
        Quad.updateBound();
 
        return Quad;
 
        /*Box s = new Box(0.1f,0.1f,0.1f);
        Geometry geom = new Geometry(“Ball”,s);
        geom.setMaterial(materials.Off());
        //geom.setQueueBucket(RenderQueue.Bucket.Translucent);
        //geom.setQueueBucket(RenderQueue.Bucket.Transparent); 
 
        return geom;*/
    }
 
    public void updateCube(int a, int b, int c, Boolean s)
    {
        if(s)
            dot[a][b][c].setMaterial(materials.On());
        else
            dot[a][b][c].setMaterial(materials.Off());
    }
 
    // not used
    public Geometry[][][] getAll()
    {
        return Grid;
    }
 
    // not used (detaching cube node instead) when changing dimension in-game
    public void detachChildren()
    {
        cube.detachAllChildren();
    }
}
