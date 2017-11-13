package com.infrosoft.mazerunner.mazerunner

import com.fasterxml.jackson.databind.introspect.ClassIntrospector

class MazeRequest {
    public String maze

    def setMaze(maze) {
        this.maze = maze
    }

    def getMaze(){
        maze
    }
}
