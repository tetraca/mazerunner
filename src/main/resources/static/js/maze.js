var maze = new Vue({
    el: "#maze-app",
    data: {
        rawMaze: '',
        solvedPath: [],
        mazeRows: [],
        cursor: { 'row': 0, 'col': 0 },
        success: false,
        errorMessage: ''
    },

    watch: {
        rawMaze: function() {
            this.solvedPath = [];
            this.success = false;

            var rawMazeRows = this.rawMaze.split("\n");

            this.mazeRows = rawMazeRows.map(function (row, rowIndex) {
                return row.split('').map(function (col, colIndex) {
                    switch(col) {
                        case '.':
                            return maze.createMazeCell('free', '');
                            break;
                        case 'A':
                            maze.cursor = { 'row': rowIndex, 'col': colIndex };
                            return maze.createMazeCell('start', '');
                            break;
                        case 'B':
                            return maze.createMazeCell('end', '');
                            break;
                        default:
                            return maze.createMazeCell('wall', '');
                            break;
                    }
                });
            });

            return this.rawMaze;
        },

        solvedPath: function() {
            if(this.solvedPath !== null && this.solvedPath.length > 0) {
                this.solvedPath.map(function(direction) {
                    maze.setCursorCell(direction);
                    maze.moveDirection(direction);
                });
            }
        },

    },

    methods: {
        submitMaze: function() {
            this.$http.post('/solve', { maze: this.rawMaze }).then(response => {
                // Success
                console.log(response);
                maze.success = response.status;
                maze.solvedPath = response.body.message;

                $('#maze-modal').modal();
            }, response => {
                // Error
                console.log(response);
                maze.success = response.status;
                maze.errorMessage = response.body.message;
                maze.solvedPath = [];

                $('#maze-modal').modal();
            });
        },


        // Mark the cell the cursor is on so that an arrow will be formatted onto it
        setCursorCell: function(direction) {
            var movementType = this.mazeRows[this.cursor.row][this.cursor.col].movementType;
            this.mazeRows[this.cursor.row][this.cursor.col] = this.createMazeCell(movementType, direction);
        },

        // Move the cursor 1 unit relative to its current point
        moveDirection: function(direction) {
            switch(direction) {
                case 'left':
                    this.moveCursor(this.cursor.row, this.cursor.col - 1);
                    break;
                case 'right':
                    this.moveCursor(this.cursor.row, this.cursor.col + 1);
                    break;
                case 'up':
                    this.moveCursor(this.cursor.row - 1, this.cursor.col);
                    break;
                case 'down':
                    this.moveCursor(this.cursor.row + 1, this.cursor.col);
                    break;
            }
        },

        // Move the cursor absolutely to a specific point of the grid
        moveCursor: function(row, col) {
             this.cursor = this.createCursor(row, col);
        },

        createCursor: function(row, col) {
            return { 'row': row, 'col': col };
        },

        createMazeCell: function(movementType, direction) {
             return { 'movementType': movementType, 'direction': direction }
        }
    }

});
