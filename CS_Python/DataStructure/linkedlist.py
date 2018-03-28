class Node:
    def __init__(self, data):
        self.data = data
        self.next = None

class LinkedList:
    def __init__(self):
        self.head = None

    def addToFront(self, new_data):
        newNode = Node(new_data)
        newNode.next = self.head
        self.head = newNode

    def addToTail(self, new_data):
        newNode = Node(new_data)
        temp = self.head
        if (temp == None):
            self.head = newNode
        else:
            while (temp.next != None):
                temp = temp.next
            temp.next = newNode

    def deleteNode(self, node_data):
        found = False
        current = self.head
        if (current == None):
            return
        elif (current.data == node_data):
            if (current.next == None):
                self.head = None
            else:
                self.head = current.next
        else:
            while (current.next != None):
                prev = current
                current = current.next
                if (current.data == node_data):
                    prev.next = current.next
                    print ("Node found and deleted")
                    found = True
            if (not found):
                print("Node does not exist")

    def printList(self):
        temp = self.head
        while(temp):
            print (temp.data)
            temp = temp.next

#MAIN RUN
if __name__=='__main__':
    lst = LinkedList()
    lst.head = Node(1)
    second = Node(2)
    third = Node(3)
    lst.head.next = second
    second.next = third

    lst.printList()
    print("Add node 5 to the head of the list")
    lst.addToFront(5)
    lst.printList()

    print("Add node  to the end of the list")
    lst.addToTail(8)
    lst.printList()

    print("Delete node")
    lst.deleteNode(5)
    lst.deleteNode(8)
    lst.deleteNode(8)
    lst.printList()

