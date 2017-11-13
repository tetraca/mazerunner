package com.infrosoft.mazerunner.mazerunner

import org.springframework.web.bind.annotation.RestController
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.ui.Model


@RestController
class MazeController {
    @GetMapping("/solve")
    def Show() {
        return new MazeResponse('error', 'Please POST a maze to have it solved.')
    }

    @PostMapping("/solve")
    def solveMaze(@RequestBody MazeRequest mazeRequest) {
        def submittedMaze = new Maze(mazeRequest.maze.split("\n"))
        def solver = new MazeSolver(submittedMaze)

        def pathResult = solver.solve()
        return new MazeResponse('success', pathResult)
    }
}
