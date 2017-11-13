package com.infrosoft.mazerunner.mazerunner


class Maze {
    // The first two are only strings to simply some comparisons
    private final String START_POINT_CHAR = "A"
    private final String END_POINT_CHAR   = "B"
    private final char   OPEN_SPACE_CHAR  = '.'

    private String[] Rows
    private int      MaxRows
    private int      MaxCols
    private Point    MazeStart
    private Point    MazeEnd
    private Point    Cursor

    Maze(String[] rows) {
        MaxRows = rows.length
        MaxCols = rows[0].size()

        // Ensure that maze is a uniform width
        def expectedMazeSize = MaxRows * MaxCols
        def actualMazeSize = rows.collect({ it.size() }).sum()

        if(actualMazeSize != expectedMazeSize) {
            throw new IllegalArgumentException("Your maze must have a uniform width and height.")
        }


        // Ensure that the supplied maze has exactly 1 start point and end point.
        def startPoints = rows.collect { it.findAll({ it == START_POINT_CHAR}).size() }
        def endPoints   = rows.collect { it.findAll({ it == END_POINT_CHAR  }).size() }
        if(startPoints.sum() != 1 || endPoints.sum() != 1) {
            throw new IllegalArgumentException("Your maze must have exactly one start point and one end point.")
        }

        // Set start point, end point, and put the cursor on the start point
        def startPointRow = startPoints.indexOf(1)
        def startPointCol = rows[startPointRow].findIndexOf { it == START_POINT_CHAR }

        MazeStart = new Point(startPointCol, startPointRow)
        Cursor    = new Point(MazeStart)

        def endPointRow   = endPoints.indexOf(1)
        def endPointCol   = rows[endPointRow].findIndexOf { it == END_POINT_CHAR }

        MazeEnd = new Point(endPointCol, endPointRow)

        Rows = rows
    }

    //
    // Checks to see if it is okay to move the cursor to the specified point
    //
    private def isMoveValid(Point movementCoordinate) {
        // Bounds check
        def withinRowBounds = (movementCoordinate.row < MaxRows) && (movementCoordinate.row >= 0)
        def withinColBounds = (movementCoordinate.col < MaxCols) && (movementCoordinate.col >= 0)
        if(!withinRowBounds || !withinColBounds) {
            return false
        } else {
            // As long as we're in bounds, the move should be valid if we're in open space
            getMazeChar(movementCoordinate) == OPEN_SPACE_CHAR || getMazeChar(movementCoordinate) == END_POINT_CHAR.charAt(0)
        }
    }

    //
    // Gets the character contained at the specified point of the maze
    //
    private def getMazeChar(Point coordinate) {
        Rows[coordinate.row].charAt(coordinate.col)
    }

    //
    // Get all possible moves given the current cursor position
    //
    def ActionPoint[] getPossibleMoves() {
        def position = getCursorPosition()

        // Up, down, left and right
        def potentialMoves = [
                new ActionPoint(new Point(position.col, position.row + 1), 'down'),
                new ActionPoint(new Point(position.col, position.row - 1), 'up'),
                new ActionPoint(new Point(position.col - 1, position.row), 'left'),
                new ActionPoint(new Point(position.col + 1, position.row), 'right')
        ]

        def possibleMoves = []
        for(move in potentialMoves) {
            if(isMoveValid(move.Point)) {
                possibleMoves << move
            }
        }

        possibleMoves
    }

    //
    // Moves the cursor to the specified point
    //
    def moveCursor(Point movementCoordinate) {
        Cursor.col = movementCoordinate.col
        Cursor.row = movementCoordinate.row
    }

    def getCursorPosition() {
        Cursor.getLocation()
    }

    //
    // The maze is solved when the cursor is moved onto the end point
    //
    def isSolved() {
        Cursor.equals(MazeEnd)
    }

}
