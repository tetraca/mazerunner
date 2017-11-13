package com.infrosoft.mazerunner.mazerunner

class ActionPoint {
    public final Point Point
    public final String Direction

    ActionPoint(Point point, String direction) {
        Point     = point
        Direction = direction
    }

    String toString() {
        Direction + " to " + Point.toString()
    }
}
