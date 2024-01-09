package algos

import "strings"

func PermuteString(str string) []string {
	return recursiveString(strings.Split(str, ""), 0)
}

func recursiveString(str []string, index int) []string {
	if index == len(str) {
		return []string{strings.Join(str, "")}
	}

	var perms []string
	for i := index; i < len(str); i++ {
		str[i], str[index] = str[index], str[i]
		perms = append(perms, recursiveString(str, index+1)...)
		str[i], str[index] = str[index], str[i]
	}

	return perms
}
