package util;

import java.awt.Point;
import java.util.LinkedList;
import java.util.List;

public class PathSearch {

    public  static List<Point> findPath(int[][] map, Point start, Point goal){
        StarNode startNode = new StarNode(start);
        StarNode goalNode = new StarNode(goal);

        LinkedList<StarNode> open = new LinkedList<>();
        LinkedList<StarNode> close = new LinkedList<>();

        startNode.searchParent = null;

        open.add(startNode);
        close.add(startNode);

        while (!open.isEmpty()){
            StarNode node = open.removeFirst();
            if (node.equals(goalNode)){
                return construct(node);
            }else{
                for (StarNode n : node.getNeighbors()){
                    if (!open.contains(n) && !close.contains(n) && n.isHit(map)) {
                        n.searchParent = node;
                        open.add(n);
                        close.add(n);
                    }
                }
            }
        }
        return null;
    }

    public static List<Point> construct(StarNode node){
        LinkedList<Point> path = new LinkedList<>();
        while (node != null){
            path.addFirst(node.location);
            node = node.searchParent;
        }
        return path;
    }


}
