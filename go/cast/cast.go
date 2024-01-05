package cast

import (
	"fmt"
	"strconv"
)

func ToInt(arg interface{}) int {
	var val int
	switch s := arg.(type) {
	case string:
		var err error
		val, err = strconv.Atoi(s)
		if err != nil {
			panic("error converting string to int " + err.Error())
		}
	default:
		panic(fmt.Sprintf("unhandled type for int casting %T", arg))
	}

	return val
}
