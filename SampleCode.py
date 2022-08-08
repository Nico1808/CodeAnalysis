# This comment is useless
def uselessFunction(someString):
    s = someString
    print (s)
    #useless

def uselessFunction2(someInt):

    i = someInt
    print (i)

uselessFunction("abcdef")
uselessFunction2(123)

# Multiline #Comments
# do not really exist
# in python
n = 314
# You# just put multiple
# comments in consecutive
# lines

#sample for-loops:

"""
erkennt die regex das?
müssten jetzt 14 kommentare sein
"""

for x in "busfahrer":
    print (x)

for i in range(1, 20):
    print(i)
    if i % 2 == 0:
        for j in range(1, 3):
            if j == 2:
                print (j)

for i in range(1, 100, 5):
    if i % 10 == 0:
        print(i)

berufe = ["Busfahrer", "Jurist", "Dozent"]

for s in berufe:
    print (s)

#sample while-loops

i = 5
a = 2

while i < 10:
    print(i)
    i += 1
    while a > 0:
        print(a)
        a -= 1

while i == 0:
    print("hä")

#this is not a while loop:
# while a ==b: #
#   print("blahblah")

while True:
    print("blah")
    if a <= 90:
        break

while True:
    print(i)
    i += 1
    if i >= 15:
        break