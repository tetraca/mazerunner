package com.infrosoft.mazerunner.mazerunner

class SolveRequest {
    private String RequestMaze

    def getRequestMaze() {
        Maze
    }

    def getMaze() {
        new Maze(RequestMaze.split("\n"))
    }

    def setMaze(String requestMaze) {
        this.RequestMaze = requestMaze
    }
}
