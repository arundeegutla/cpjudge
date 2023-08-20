# Arup Guha
# 9/1/2018
# Solution to 2018 UCF Locals problem: World Cup Fever

from collections import deque

def main():

    # Get number of players.
    n = int(input(""))

    # Read in both teams (team1 = 0 to n-1, team2 = n to 2n-1)
    players = []
    inp = input("").split()
    inp2 = input("").split()
    for i in range(len(inp2)):
        inp.append(inp2[i])
        
    for i in range(2*n):
        pos = []
        pos.append(int(inp[2*i]))
        pos.append(int(inp[2*i+1]))
        players.append(pos)

    # Store my graph as an adjacency list.
    graph = []
    for i in range(n):
        graph.append([])

    # Loop through all pairs of problems
    for i in range(n):
        for j in range(i+1,n):

            inbetween = False

            # See if any other players are in between.
            for k in range(0, 2*n):

                # Don't test these.
                if k == i or k == j:
                    continue

                # See if player k is in the way of players i and j.
                if block(players, i, j, k):
                    inbetween = True
                    break

            # i and j can kick the ball to each other - add edges.
            if not inbetween:
                graph[i].append(j)
                graph[j].append(i)

    # Print the result.
    print(bfs(graph,0,n-1))

# Returns true if the line seg players[start]<->players[end] is blocked
# by players[mid], false otherwise.
def block(players, start, end, mid):

    # Mid isn't on the line between start to end.
    if (players[end][0]-players[start][0])*(players[mid][1]-players[start][1]) != (players[mid][0]-players[start][0])*(players[end][1]-players[start][1]):
        return False

    # Check on x if x's are distinct.
    if players[start][0] != players[end][0]:
        return players[start][0] <= players[mid][0] <= players[end][0] or players[end][0] <= players[mid][0] <= players[start][0]

    # If we get here, the y's must be distinct.
    return players[start][1] <= players[mid][1] <= players[end][1] or players[end][1] <= players[mid][1] <= players[start][1]

# Runs a bfs on graph from vertex s to e. Returns shortest distance.
# Returns -1 if there is no path.
def bfs(graph,s,e):

    # Add s into the queue.
    myq = deque()
    myq.append(s)
    dist = [-1]*len(graph)
    dist[s] = 0

    # Run bfs.
    while len(myq) > 0:

        # Get the next 
        cur = myq.popleft()
        if cur == e:
            return dist[cur]

        # Enqueue new neighbors.
        for x in graph[cur]:
            if dist[x] == -1:
                dist[x] = dist[cur] + 1
                myq.append(x)

    # Never made it.
    return -1

# Run it!
main()        

        

    
    
