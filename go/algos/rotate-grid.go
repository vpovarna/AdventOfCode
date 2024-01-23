package algos

func RotateStringGrid(grid [][]string) [][]string {
	rotated := make([][]string, len(grid[0]))
	for i := range rotated {
		rotated[i] = make([]string, len(grid))
	}

	for i := 0; i < len(grid); i++ {
		for j := 0; j < len(grid[0]); j++ {
			rotated[len(grid[0])-1-j][i] = grid[i][j]
		}
	}
	return rotated
}
