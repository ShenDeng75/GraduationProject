import re

if __name__ == "__main__":
    dic = {"a": "a ", "b": " b", "c": "c", "d": "d "}
    print(dic.values())
    out = "\t".join([str(x).replace(" ", "  ").strip() for x in dic.values()])
    print(out)
