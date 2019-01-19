Exercise 1:

    1.  main
            x = 5;
            blank -> Point(x = 1, y = 2)
            riddle(x -> main.x, p -> blank)
                x = 12

    2.  Output of the program:
        15
        5
        1
        2

    3.  The blank object is mutable because its fields can be changed.

Exercise 2:

    1.  main
            blank -> Point(x = 5, y = 8)
            rect -> Rectangle(x = 0, y = 2, w = 4, h = 4)
            center -> findCenter(box -> rect)
                          x = 1
                          y = 3
                   -> Point(x = 1, y = 3)

            dist   -> distance(p1 -> center, p2 -> blank)
                          dx = 4
                          dy = 3

    2.  Output of the program: 5.0

Exercise 3:

    1.  The state of the program just before the end of main:
        p1 -> Point(x = 5, y = 8)
        p2 -> Point(x = 5, y = 8)
        box1 -> Rectangle(x = 0, y = 2, w = 9, h = 11)

    2.  Output of the program:
        (5, 8)
        (5, 8)

    3.  At the end of main, p1 and p2 are not aliased because they're
        both their own objects ("new" creates a new object)

Exercise 4:

    If I only use integers to calculate the factorials of integers, eventually,
    the answers for the factorials won't be right anymore. This is because an
    integer has a limit of 4 bytes (32 bits) of storage, so an integer can only
    go up to around 2 billion. Anything above that, the integer value will go
    all wack.

    BigInteger objects are immutable because all the fields in them are fixed.

Exercise 5:

    Refer to my code in "Exercise5.java".




