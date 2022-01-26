package graph;
import java.util.ArrayList;
import java.util.HashMap;

import javax.imageio.plugins.tiff.TIFFTag;
/**
 * This class implements general operations on a graph as specified by UndirectedGraphADT.
 * It implements a graph where data is contained in Vertex class instances.
 * Edges between verticies are unweighted and undirected.
 * A graph coloring algorithm determines the chromatic number. 
 * Colors are represented by integers. 
 * The maximum number of vertices and colors must be specified when the graph is instantiated.
 * You may implement the graph in the manner you choose. See instructions and course material for background.
 */
 
 public class UndirectedUnweightedGraph<T> implements UndirectedGraphADT<T> {
   // private class variables here.
   
   private int MAX_VERTICES;
   private int MAX_COLORS;
    // TODO: Declare class variables here.
   private int numVertices;
   private int numEdges;
   private boolean[] colorValues;
   private ArrayList<Vertex<T>> vertices;
   private boolean adjMat[][];
   private HashMap<T, Integer> numMap;
  //  private ArrayList<ArrayList<Integer> > adjMat;
   
   /**
    * Initialize all class variables and data structures. 
   */   
   public UndirectedUnweightedGraph (int maxVertices, int maxColors){
      MAX_VERTICES = maxVertices;
      MAX_COLORS = maxColors; 
     // TODO: Implement the rest of this method.
      this.vertices = new ArrayList<Vertex<T>>(maxVertices);
      this.colorValues = new boolean[maxColors];
      this.numMap = new HashMap<T, Integer>(maxVertices);
      this.adjMat = new boolean[maxVertices][maxVertices];
      this.numVertices = 0;
      this.numEdges = 0;
   }

   /**
    * Add a vertex containing this data to the graph.
    * Throws Exception if trying to add more than the max number of vertices.
   */
   public void addVertex(T data) throws Exception {
    // TODO: Implement this method.
      if (numVertices >= MAX_VERTICES){
        throw new Exception();
      }
      vertices.add(new Vertex<T>(data));
      numMap.put(data, numVertices);
      numVertices++;
   }
   
   /**
    * Return true if the graph contains a vertex with this data, false otherwise.
   */   
   public boolean hasVertex(T data){
    // TODO: Implement this method.
      for (Vertex<T> vertice : vertices){
        if (vertice.getData().equals(data))
          return true;
      }
      return false;
   } 

   /**
    * Add an edge between the vertices that contain these data.
    * Throws Exception if one or both vertices do not exist.
   */   
   public void addEdge(T data1, T data2) throws Exception{
    // TODO: Implement this method.
     if (!hasVertex(data1) || !hasVertex(data2)){
       throw new Exception();
     }
     adjMat[numMap.get(data1)][numMap.get(data2)] = true;
     adjMat[numMap.get(data2)][numMap.get(data1)] = true;
     numEdges++;
   }

   /**
    * Get an ArrayList of the data contained in all vertices adjacent to the vertex that
    * contains the data passed in. Returns an ArrayList of zero length if no adjacencies exist in the graph.
    * Throws Exception if a vertex containing the data passed in does not exist.
   */   
   public ArrayList<T> getAdjacentData(T data) throws Exception{
    // TODO: Implement this method.
        if (!hasVertex(data)){
          throw new Exception();
        }
        ArrayList<T> adjList = new ArrayList<T>(MAX_VERTICES-1);
        for (Vertex<T> vertice : vertices){
          if(adjMat[numMap.get(vertice.getData())][numMap.get(data)] == true) {
            adjList.add(vertice.getData());
          }
        }
      
      return adjList;
   }
   
   /**
    * Returns the total number of vertices in the graph.
   */   
   public int getNumVertices(){
    // TODO: Implement this method.
      return numVertices;
   }

   /**
    * Returns the total number of edges in the graph.
   */   
   public int getNumEdges(){
    // TODO: Implement this method.
      return numEdges;
   }

   /**
    * Returns the minimum number of colors required for this graph as 
    * determined by a graph coloring algorithm.
    * Throws an Exception if more than the maximum number of colors are required
    * to color this graph.
   */   
   public int getChromaticNumber() throws Exception{
    // TODO: Implement this method.
    int highestColorUsed = -1;
    int colorToUse = -1;
    for (Vertex<T> vertice : vertices) {
      if (vertice.getColor() == -1) {
        colorToUse = getColorToUse(vertice);
        vertice.setColor(colorToUse);
        highestColorUsed = Math.max(highestColorUsed, colorToUse);
      }
    }
    if (highestColorUsed + 1 > MAX_COLORS) {
      throw new Exception();
    }
    return highestColorUsed + 1;
   }

   private int getColorToUse(Vertex<T> vertice) throws Exception{
    int colorToUse = -1;
    boolean[] adjColorsUsed = new boolean[MAX_COLORS];
    ArrayList<Vertex<T>> adjVertsList = getAdjacentVertices(vertice);
    for (Vertex<T> curVert: adjVertsList){
      if(curVert.getColor() != -1){
        adjColorsUsed[curVert.getColor()] = true;
      }
    }
    int i = 0;
    while (adjColorsUsed[i] == true){
      i++;
      if (i + 1 > MAX_COLORS){
        throw new Exception("All colors have been used");
      }
    }
    colorToUse = i;
    return colorToUse;
   }

   private ArrayList<Vertex<T>> getAdjacentVertices(Vertex<T> vertice) {
    ArrayList<Vertex<T>> adjList = new ArrayList<Vertex<T>>(MAX_VERTICES-1);
    for (Vertex<T> curVert : vertices){
      if(adjMat[numMap.get(vertice.getData())][numMap.get(curVert.getData())] == true) {
        adjList.add(curVert);
      }
    }
    return adjList;
   }
}