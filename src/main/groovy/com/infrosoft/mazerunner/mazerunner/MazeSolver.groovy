package com.infrosoft.mazerunner.mazerunner

import java.util.concurrent.LinkedTransferQueue

class MazeSolver {
    private Maze Maze
    private Queue<Point> OpenSet
    private ArrayList<Point> ClosedSet
    private Hashtable<Point, ActionPoint> PathInformation

    MazeSolver(Maze maze) {
        Maze = maze
        OpenSet = new LinkedTransferQueue<Point>()
        PathInformation = new Hashtable<Point,ActionPoint>()
        ClosedSet = new ArrayList<Point>()
    }

    //
    // Traverse the maze with a Breadth first search to find a path from A -> B
    //
    def solve() {
        def startPosition = Maze.getCursorPosition()
        PathInformation.put(startPosition, new ActionPoint(null, null))
        OpenSet.add(startPosition)

        while(!OpenSet.isEmpty()) {
            def parentMove = OpenSet.remove()
            Maze.moveCursor(parentMove)

            if(Maze.isSolved()) {
                return constructPath(parentMove)
            }

            for(possibleMove in Maze.getPossibleMoves()) {
                if(ClosedSet.contains(possibleMove.Point)) {
                    continue
                }

                if(!OpenSet.contains(possibleMove.Point)) {
                    def action = new ActionPoint(parentMove, possibleMove.Direction)
                    PathInformation.put(possibleMove.Point, action)
                    OpenSet.add(possibleMove.Point)
                }
            }

            ClosedSet.push(parentMove)
        }

        throw new RuntimeException("Your maze has no solution.")
    }

    //
    // Traverses the path list backwards to get the completed path
    //
    def constructPath(Point parentMove) {
        def actionList = []

        def row = PathInformation.get(parentMove)
        while(row.Direction != null) {
            actionList << row.Direction
            row = PathInformation.get(row.Point)
        }

        actionList.reverse()
    }
}
