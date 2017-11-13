package com.infrosoft.mazerunner.mazerunner

import groovy.transform.EqualsAndHashCode

@EqualsAndHashCode
class Point {
    int col
    int row

    Point(int col, int row) {
        this.col = col
        this.row = row
    }

    Point(Point point) {
        col = point.col
        row = point.row
    }

    Point getLocation() {
        new Point(col, row)
    }

    String toString() {
        "(" + this.col.toString() + ", " + this.row.toString() + ")"
    }

}
