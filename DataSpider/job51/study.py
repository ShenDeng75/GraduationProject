import datetime
import json
import random
import re
import time


def format_salary(sal):
    sala = re.split('[·]', sal)
    return sala[0][:-1]


if __name__ == "__main__":
    stime = random.random()
    print(stime)
