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
	case uint8:
		var err error
		str := fmt.Sprintf("%c", s)
		val, err = strconv.Atoi(str)
		if err != nil {
			panic("error converting string to int " + err.Error())
		}
	default:
		panic(fmt.Sprintf("unhandled type for int casting %T", arg))
	}

	return val
}

func ToASCIICode(arg interface{}) int {
	var asciVal int
	switch arg.(type) {
	case string:
		str := arg.(string)
		if len(str) != 1 {
			panic("Can only convert ascii code for string of length 1")
		}
		asciVal = int(str[0])
	case byte:
		asciVal = int(arg.(byte))
	case rune:
		asciVal = int(arg.(rune))
	default:
		panic("unsupported type")
	}

	return asciVal
}

func ASCIIIntToChar(code int) string {
	return string(rune(code))
}

func MapSliceOfStringToInt(strings []string) []int {
	output := []int{}
	for _, s := range strings {
		output = append(output, ToInt(s))
	}

	return output
}
