package uj.wmii.pwj.collections;
import java.util.*;


public class BattleshipImplementation implements BattleshipGenerator {

    static class point {
        int x;
        int y;
        String prev;

        point(int x, int y, String prev) {
            this.x = x;
            this.y = y;
            this.prev = prev;
        }
    }

    boolean[][] map = new boolean[10][10];
    boolean[][] space = new boolean[10][10];

    public point getRandomCoords() {
        List<point> coords = new ArrayList<>();

        for (int i = 0; i < 10; i++)
            for (int j = 0; j < 10; j++)
                if (!space[i][j])
                    coords.add(new point(i, j, "ok"));

        Collections.shuffle(coords);
        return coords.getFirst();
    }

    void takeSpace (int x, int y){
        map[x][y] = true;
        for (int i = -1; i < 2; i++)
            for (int j = -1; j < 2; j++)
                if (x+i >= 0 && y+j >= 0 && x+i < 10 && y+j < 10)
                    space[x+i][y+j] = true;
    }

    point emplace (int x, int y, int size, String prev){
        List<point> points = new ArrayList<>();
        point output;

        if (x+1 < 10 && !space[x+1][y] && prev != "right")
            points.add(new point (x+1,y,"left"));
        if (x-1 >= 0 && !space[x-1][y] && prev != "left")
            points.add(new point (x-1,y,"right"));
        if (y+1 < 10 && !space[x][y+1] && prev != "up")
            points.add(new point (x,y+1,"down"));
        if (y-1 >= 0 && !space[x][y-1] && prev != "down")
            points.add(new point (x,y-1,"up"));

        if (points.isEmpty())
            return null;

        Collections.shuffle(points);
        output = points.getFirst();

        if (size > 1)
            output = emplace(output.x, output.y, size - 1, output.prev);

        if (output != null)
            takeSpace (x, y);

        return output;

    }

    public String generateMap() {
        String output = "";

        List<Integer> sizes = Arrays.asList(4, 3, 3, 2, 2, 2, 1, 1, 1, 1);
        for (int i = 0; i < 10; i++){
            point coords;
            do{
                coords = getRandomCoords();
                coords = emplace(coords.x, coords.y, sizes.get(i), "ok");}
            while(coords == null);
        }

        for (int i = 0; i < 10; i++)
            for (int j = 0; j < 10; j++)
                if (map[i][j])
                    output += "#";
                else
                    output += ".";

        return output;
    }

    BattleshipGenerator defaultInstance (){
        return new BattleshipImplementation ();
    }
}
