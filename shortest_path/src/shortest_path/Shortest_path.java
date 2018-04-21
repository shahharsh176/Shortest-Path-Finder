/*
    Program    : Finding the Shortest Path between Multiple Sources and Multiple Destinations

    Created by : Group 33

                 Darshan Shah(201501094)
                 Dhyey Thaker(201501108)
                 Hardik Udeshi(201501113)
                 Harsh Shah(201501096)

    Course     : Data Structures and Algorithms(DSA)

*/

package shortest_path;          //Package

//Importing Necessary Files
import java.util.Scanner;
import java.util.ArrayList;
import java.io.*;
import java.nio.channels.FileChannel;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.HashSet;
import java.util.InputMismatchException;
import java.util.Iterator;
import java.util.Scanner;
import java.util.Set;

//Class Tree
class tree implements Serializable          //for File handlind,the class tree should be implemented by Serializable interface
{
    class Node implements Serializable      //Class Node inside Class Tree and all the classes inside a particular class should also be implemented by Serializable Interface
    {
        //Declaring the Variables
        private int vertex;         //Vertex
        private float weight;       //Weight
        private Node left;          //Left Part
        private Node right;         //Right Part

        //Constructor of Class Node
        public Node(int v,float w)
        {
            //Assigning the values to variables in Constructor
            this.vertex = v;
            this.weight = w;
            this.left = null;
            this.right = null;
        }
        
        //Setters and Getters
        public void set_vertex(int v)   //Set method for Setting a vertex
        {
            vertex = v;
        }
        
        public void set_weight(float w)   //Set Method for setting a weight
        {
            weight = w;
        }
        
        public int get_vertex()     //Get Method for getting the vertex
        {
            return vertex;
        }
        
        public float get_weight()   //Get Method for getting the Weight
        {
            return weight;
        }
         
    }
    
    Node root;      //Declaration the root of a tree with datatype Node
    
    public Node get_root()  //Get Method for Getting the Root of a specific Tree
    {
        return this.root;
    }
    
    public void Insert(int v,float w)   //Insert Method for inserting a tree
    {
        root = InsertNode(root,v,w);
    }
    
    public Node InsertNode(Node r,int v,float w)    //InsertNode Method for inserting a node into the Tree
    {
        if(r==null)             //If Root = NULL
        {
            r = new Node(v,w);  //Then we need to insert a root
            return r;       //Returning the tree
        }
        else                    //If Root is already there i.e Root is not equal to NULL
        {
            if(v < r.get_vertex())      //If the vertex of the Node to be inserted is less than Root
            {
                r.left  = InsertNode(r.left,v,w);       //If so then we need to insert in the left part of the tree
            }
            
            else if(v > r.get_vertex())      //If the vertex of the Node to be inserted is greater than Root
            {
                r.right  = InsertNode(r.right,v,w);       //If so then we need to insert in the right part of the tree
            }
            return r;       //Returning the root
        }
          
    }
    
    public void inorder(Node r)         //Method for Inorder Traversal
    {
        if(r==null)         //If a tree with no root
        {
            return;         //If so then no need to search,just simply return
        }
        
        //Inorder Traversal Logic
        inorder(r.left);    //Recursively calling the Inorder function while traversing the left part of a tree
        System.out.println("Vertex: "+r.get_vertex()+"\tWeight: "+r.get_weight());      //Printing the Info
        inorder(r.right);    //Recursively calling the Inorder function while traversing the right part of a tree       
            
    }
    
    Node search(int v,Node temp)    //Method for searching the Node with Vertex
    {    
        if(temp==null || temp.vertex == v )     //If the root is equal to Null and if the vertex to be searched is what we want
        {
            return temp;        //Return root
        }
           
        else        
        {
            if(v < temp.vertex)         //If the node with vertex to be searched is less than the root
            {
                return search(v,temp.left);     //If so then need to search in the left part of a root
            }
            
            else                        //If the node with vertex to be searched is greater than the root                      
            {
               return search(v,temp.right);     //If so then need to search in the right part of a root
            }
                
        }
      
    }
    Node weight(int v,Node temp)        //Method for searching the Node with weight
    {
        
        if(temp==null || temp.vertex == v )     //If the root is equal to Null and if the weight to be searched is what we want    
        {   
            return temp;        //Return root
        }
        
        else
        {
            if(v < temp.vertex)         //If the node with weight to be searched is less than the root
            {
                return weight(v,temp.left);     //If so then need to search in the left part of a root
            }
            
            else                        //If the node with weight to be searched is greater than the root 
            {
               return weight(v,temp.right);     //If so then need to search in the right part of a root
            }
                
        }
      
    }
 
}

//Class DijkstraAlgo:Dijkstra's Algorithm
class DijkstraAlgo  
{
    //Declaring the Variables
    private float distances[];          //An array which stores the minimum distance from particular source to all the other nodes
    private Set<Integer> dist_set;      //For finding the Minimum distance from source to destination
    private Set<Integer> vertex_set;    //For storing the vertex which is not yet visited
    private int number_of_nodes;        //For total number of Nodes
    private HashMap<Integer, Integer> hmap;     //Hashmap for printing the path from source to destination
    private tree[] bst;     //Array of Object tree
    
    //Constructor of DijkstraAlgo
    public DijkstraAlgo(int number_of_nodes,tree[] t)
    {
        //Assigning the variables in a Constructor
        this.number_of_nodes = number_of_nodes;
        distances = new float[number_of_nodes+1];
        dist_set = new HashSet<Integer>();
        vertex_set = new HashSet<Integer>();
        hmap = new HashMap<Integer, Integer>();
        this.bst = t;
    }
    
    public float[] get_distance()       //Get Method for getting the distance
    {
        return distances;
    }
    
    public HashMap<Integer, Integer> get_hmap()     //Get Method for Getting the HashMap
    {
        return hmap;
    }
 
    public void dijkstra_algorithm(int source)      //Main Method for Dijkstra's Algorithm
    {
        int evaluationNode;         //Variable for Evaluation Node
       
        for (int i = 1; i <= number_of_nodes; i++)      //For Loop for initializing the distances to infinity
        { 
            distances[i] = Float.MAX_VALUE;             //Assigning the Maximum Value i.e infinity
        }
 
        vertex_set.add(source);     //Adding the source to vertex_set HashSet
        distances[source] = 0;      //Initializing the distance of source index to be 0
        hmap.put(source,0);         //Initializing the hashMap
        
        while (!vertex_set.isEmpty())       //Loop that is to be executed until the vertex_set is not emptied 
        {
            evaluationNode = getNodeWithMinimumDistance();  //Storing the Node that is to be returned from getNodeWithMinimumDistance() Method
            vertex_set.remove(evaluationNode);      //Removing the Evaluation Node from vertex_set
            dist_set.add(evaluationNode);           //Adding the Evaluation Node into the dist_set
            Neighbours(evaluationNode);             //Calling the Neighbours() Method
        } 
    }
 
    private int getNodeWithMinimumDistance()        //Method which returns the index at which the Minimum Distance is there
    {
        //Declaring the Variables
        float min ;     //For storing the minimum distance
        int node = 0;   //Assigning the node variable to 0
 
        //Iterator for iterating the vertex_set
        Iterator<Integer> iterator = vertex_set.iterator();     
        node = iterator.next();     
        
        min = distances[node];      //Assigning the min to distance at 0th index
        
        for (int i = 1; i <= distances.length; i++)     //For loop for finding the minimum distance and index at which it is minimum
        {
            if (vertex_set.contains(i))     //If vertex_set contains particular node
            {
                if (distances[i] <= min)    //If distance at ith index is less than or equal to min
                {
                    min = distances[i];     //Then Assigning min that distance
                    node = i;               //Updating the index i.e node			
                }
            }
        }
        return node;        //Returning the index i.e node
    }
 
    private void Neighbours(int evaluationNode)     //Method for finding the Neighbours
    {
        //Declaring the variables
        float edgeDistance = -1;    //For weight of particular Edge
        float newDistance = -1;     //For updating the distance
 
        for (int destinationNode = 1; destinationNode <= number_of_nodes; destinationNode++)    //For Loop for finding the minimum distance from one source to all other Nodes
        {
            if (!dist_set.contains(destinationNode))    //If dist_set donot contain the destination Node
            {
                if ( bst[destinationNode].search(evaluationNode, bst[destinationNode].get_root())!=null)    //If the vertex of destination node is present in the tree with root as evaluation Node
                {
                    
                    if( bst[destinationNode].weight(evaluationNode, bst[destinationNode].get_root()) !=null)    //If the weight of destination node is present in the tree with root as evaluation Node
                    {
                        edgeDistance = bst[destinationNode].weight(evaluationNode, bst[destinationNode].get_root()).get_weight();   //Assigning the weight between two nodes to edgeDistance
                    }
                    
                    newDistance = distances[evaluationNode] + edgeDistance;     //Updating the newDistance by adding the value at evaluation Node's index with edgeDistance
                    
                    if (newDistance < distances[destinationNode])       //If newDistance is less than the weight of an edge between two nodes
                    {
                        hmap.put(destinationNode,evaluationNode);       //Putting the path between destination Node and Evaluation Node into the hashMap
                        distances[destinationNode] = newDistance;       //Updating the value at destination Node's index in distances array with newDistance
                    }
                    
                    vertex_set.add(destinationNode);        //Adding the destination Node to vertex_set
                } 
            }
        }
    }
}   

//Main Class i.e Graph
public class Shortest_path {

    private static tree[] array_graph;      //Array of Objects(Objects of Tree)
    
    public static void main(String[] args)      //Main Function
    {
        
        Scanner input = new Scanner(System.in);
        
        //Initializing all file Streams to NULL
        FileOutputStream fout=null;
        FileOutputStream fout_vertex=null;
        ObjectOutputStream oOut_vertex = null;
        ObjectOutputStream oOut = null;
        FileInputStream fin = null;
        FileInputStream fin_vertex = null;
        ObjectInputStream oIn = null;
        ObjectInputStream oIn_vertex = null;
        
        FileInputStream fin_vertex_list = null;
        ObjectInputStream oIn_vertex_list = null;
        FileOutputStream fout_vertex_list=null;
        ObjectOutputStream oOut_vertex_list = null;

        tree t;				//Declaration an Object of type tree
        String str;		//Declaring a String str
        
        ArrayList<Integer> destination_index = new ArrayList<Integer>();	//Declaring an Arraylist destination_index of datatype Integer
        Stack<String> path_stack = new Stack<String>();                         //Declaring a Stack data structure of type String
        ArrayList<String> vertex_list = new ArrayList<String>();                //DEclaring an Arraylist vertex_list of type String

        //Declaration of necessary variables
        int min_index;
        int i,v,N;
        float w,min;
        byte choice;
        String vertex_name;
        String source;
        int source_index;
        byte developer_mode=0,mode_choice;
        String s;
        
        try 
        {
            //Various files in which data is stored and from which data is retrieved
            File file_graph = new File("C:\\Users\\shahh\\Documents\\Netbeans Project\\shortest_path\\Graph.dat");
            File file_vertex = new File("C:\\Users\\shahh\\Documents\\Netbeans Project\\shortest_path\\Vertex.dat");
            File file_vertexList = new File("C:\\Users\\shahh\\Documents\\Netbeans Project\\shortest_path\\VertexList.dat");
            
            if(file_graph.exists())         //If file Graph.dat exists
            {
                //Two modes:Developer and User
                System.out.println("You are a \n1.Developer \n2.User:");    
                mode_choice = input.nextByte();
                
                //For Developer mode
                if(mode_choice == 1)
                {
                    System.out.println("Are you sure:");
                    s=input.next();
                    
                    if(s.equals("y"))
                    {
                        developer_mode = 1;         //Initializing the enable developer_mode to 1
                        
                        if(file_graph.delete() && file_vertex.delete() && file_vertexList.delete())     //If all the 3 files is Deleted
                        {
                            System.out.println("Files Deleted Sucessfully...");
                        }
                            
                        else                            //If all the 3 files is not successfully Deleted
                        {
                            System.out.println("Files Deletion UnSucessfull...");
                        }
                        System.out.println("Developer Mode ON...");
                    }

                }                                
            }
            
            else
            {
                developer_mode = 1;         //Initializing the enable developer_mode to 1
                System.out.println("Developer Mode ON...");
            }
            
            if(developer_mode == 1)                 //For developer Mode
            {
                if(!file_graph.exists())            //If Graph.dat does not exists
                {
                    //Creating all OutputStream Files
                    fout = new FileOutputStream("Graph.dat",true);
                    oOut = new ObjectOutputStream(fout);

                    fout_vertex = new FileOutputStream("Vertex.dat",true);
                    oOut_vertex = new ObjectOutputStream(fout_vertex);

                    fout_vertex_list = new FileOutputStream("VertexList.dat",true);
                    oOut_vertex_list = new ObjectOutputStream(fout_vertex_list);

                    System.out.println("Enter total no. of Nodes:");        //Asking the user for number of nodes he want to insert in a graph
                    N = input.nextInt();

                    array_graph = new tree[N];              //Array of Objects(Objects of tree)

                    vertex_list.add("NULL");                //Adding NULL to 0th index of vertex_list

                    for(i=0;i<N;i++)                        //For Loop for writing all the objects into Vertex.dat and Adding the vertex to vertex_list
                    {

                        System.out.println("Enter the Node "+(i+1)+":");    //Asking the user for all the vertex
                        vertex_name = input.next();

                        oOut_vertex.writeObject(vertex_name);       //writing objects i.e vertex_name to Vertex.dat

                        vertex_list.add(vertex_name);               //Adding the vertices to vertex_list
                    }

                    oOut_vertex_list.writeObject(vertex_list);      //Writing the objects i.e vertex_list to VertexList.dat

                    for(i=1;i<=N;i++)
                    {

                        System.out.println("Enter Connections of Node "+ (i) + "(" +vertex_list.get(i) + ")");      //Asking the user for Connected Nodes of a particular nodes

                        choice = 1;             //Assigning 1 to choice variable

                        tree bst = new tree();      //Creating an object of datatype tree

                        while(choice!=0)                //while loop for asking the user for all the connected vertex to a particular vertex   
                        {

                            System.out.println("Enter the Connected Vertex:");      //Asking the user for the connected vertex
                            vertex_name = input.next();

                            v = vertex_list.indexOf(vertex_name);           //Assigning the index of all vertex_names of vertex_list to v

                            System.out.println("Enter weight:");            //Asking the user for the weight of an edge 
                            w = input.nextFloat();

                            System.out.println("Entered Vertex:"+v);        //Printing the Entered Vertex

                            bst.Insert(v, w);                               //Inserting into the tree

                            
                            System.out.println("Do you want to continue?\n1:YES\n0:NO\nEnter your Choice:");
                            choice = input.nextByte();

                        } 

                        try
                        {    
                            oOut.writeObject(bst);                          //Writing all the trees to Graph.dat

                            System.out.println("written sucess...");
                        }

                        catch(IOException e)                                //for catching the IOException
                        {
                            System.out.println("Oops!!!!!Error Occured while writing in Graph.dat......");
                        }

                        finally
                        {
                            System.out.println("written");
                        }
                    }

                    try
                    {
                        oOut_vertex_list.writeObject(array_graph);                  //Writing the vertex_list to VertexList.dat
                    }

                    catch(IOException d)                    //for catching the IOException
                    {
                        System.out.println("Oops!!!!!Error Occured while writing in Dhoni07.dat......");
                    }

                    finally
                    {
                        //Closing all the OutputStream files
                        oOut.close();
                        fout.close();
                        fout_vertex.close();
                        oOut_vertex.close();
                        fout_vertex_list.close();
                        oOut_vertex_list.close();
                    }

                }
            }
            
            else                //User Mode
            {
                //Creating all InputStream Files
                fin = new FileInputStream("Graph.dat");
                oIn = new ObjectInputStream(fin);

                fin_vertex = new FileInputStream("Vertex.dat");
                oIn_vertex = new ObjectInputStream(fin_vertex);
                
                fin_vertex_list = new FileInputStream("VertexList.dat");
                oIn_vertex_list = new ObjectInputStream(fin_vertex_list);

                try
                {
                    int k=1;            

                    vertex_list = (ArrayList<String>)oIn_vertex_list.readObject();      //Reading the vertex_list from VertexList.dat
                    
                    array_graph = new tree[vertex_list.size()];                         //Creating an array of objects(objects of tree) of size equal to the size of vertex_list
                    array_graph[0] = null;                                              //Assigning the first index of array_graph to 0
                    
                    while(true)
                    {
                        t = (tree)oIn.readObject();                 //Reading the tree from Graph.dat

                        array_graph[k] = t;
                        System.out.println("Reading an object.......");

                        str = (String)oIn_vertex.readObject();      //Reading the vertex_name from Vertex.dat

                        //Printing all the Connections of a particular node
                        System.out.println("Connections of "+str + ":");
                        array_graph[k].inorder(array_graph[k].get_root());      
                        
                        k++;

                    }

                }
                
                catch(EOFException e)       //for catching EOFException 
                {
                        System.out.println("All the Data retrieved Successfully..");
                }
                
                catch(ClassNotFoundException e)     //for catching ClassNotFoundException
                {
                    System.out.println("Oops!!!!There is no such Class with this name.......");
                }
                
                finally
                {
                    //Closing all the InputStream files
                    fin.close();
                    fin_vertex.close();
                    fin_vertex_list.close();
                    oIn_vertex.close();
                    oIn_vertex_list.close();
                    oIn.close();
                }
                
                for(int j=1;j<vertex_list.size();j++)       //for loop for printing the vertex_list
                {
                    System.out.println(vertex_list.get(j));
                }

     
                System.out.println("Enter your Current Location:");     //Asking the user for his current location
                source = input.next();
                
                source_index = vertex_list.indexOf(source);             //Assigning the index of the source to source_index

                choice = 1;
                
                while(choice!=0)            //while loop for all destinations of the user
                {
                    System.out.println("Enter the Destination:");           //Asking all the destinations to the user
                    str = input.next();
                    destination_index.add(vertex_list.indexOf(str));        //Adding the index of destination(from the vertex_list) to destination_index

                    System.out.println("Do you want to continue??");
                    System.out.println("1 for YES\n0 for NO");
                    choice = input.nextByte();

                }
                System.out.println("Your Current Location:"+source);        //Printing the current location of the user
                
                //Printing all the destinations of the user
                System.out.println("\nYour Destinations:");               
                for(int p=0;p<destination_index.size();p++)
                {
                    System.out.println(vertex_list.get(destination_index.get(p)));
                }
                
                System.out.println("\n");
                
                int m=destination_index.size();             //Assigning size of destination_index to variable m
                float dist[];                               //for storing all the minimum distance from one source to all the other nodes
                int new_index = source_index;               //Initializing new_index with the source_index             
                
                for(int k=0;k<m;k++)                        //for loop for the multiple destinations of the user
                {
                    //Calling the DijkstraAlgorithm Class
                    DijkstraAlgo dijkstrasAlgorithm = new DijkstraAlgo(vertex_list.size()-1,array_graph);   
                    dijkstrasAlgorithm.dijkstra_algorithm(new_index);

                    //Printing the shortest path to all the Destinations                    
                    System.out.println("The Shorted Path to all Destinations are ");
                    dist = dijkstrasAlgorithm.get_distance();
                    
                    min = dist[destination_index.get(0)];           //Initialing min with the value at particular index(value at 0th index of destination_index) of dist array        
                    min_index = 0;                                  //Initialing the min_index to 0
                    
                    for(int l = 0; l < destination_index.size(); l++)       //for loop for finding the minimum index i.e min_index
                    {
                        if(dist[destination_index.get(l)] < min)            //If the value at particular index(value at lth index of destination_index) of dist array is less than min
                        {
                            min = dist[destination_index.get(l)];           //If so then assigning that value to min
                            min_index = l;                                  //If so then assigning that index to min_index
                        }
                        
                        System.out.println(vertex_list.get(new_index) + " to " +vertex_list.get(destination_index.get(l))+ " is "+ dist[destination_index.get(l)]);     //Printing the minimum distance from one location to other
                    }
                     
                    //Printing the Closest Destination from the source
                    System.out.print("Closest Destination from ");
                    System.out.println(vertex_list.get(new_index) + " to " +vertex_list.get(destination_index.get(min_index))+ " is "+ dist[destination_index.get(min_index)]);
                    
                    new_index = destination_index.get(min_index);       //Updating the new_index by the value at min_index of destination_index
                    
                    int hmap_index = destination_index.get(min_index);      //Initializing the min_index with the value at min_index of destination_index

                    while(dijkstrasAlgorithm.get_hmap().get(hmap_index) !=0)        //while loop for storing the path into the stack and in to the hashMap
                    {
                        path_stack.push(vertex_list.get(dijkstrasAlgorithm.get_hmap().get(hmap_index)));
                        hmap_index = dijkstrasAlgorithm.get_hmap().get(hmap_index);
                    }
                    
                    while(!path_stack.empty())      //while loop for printing the path
                    {
                        System.out.print(path_stack.peek() + "----->" );
                        path_stack.pop();
                    }
                    
                    System.out.print(vertex_list.get(destination_index.get(min_index)));    //printing the destination 
                    System.out.println("");

                    System.out.println("Reached at "+vertex_list.get(destination_index.get(min_index)) + ".....");      //Printing that the user is reached to one of his destination
                    destination_index.remove(min_index);        //Once the user reaches to one of his destination then that destination is to be removed from the destination_index

                }  
            }
        }
        
        catch (IOException ex)      //catching the IOException
        {
            System.out.println("\nOut-->Error Occured: " + ex);
        }    
    }
}
