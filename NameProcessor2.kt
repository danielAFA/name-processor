package edu.assignment1

import java.io.File

class NameProcessor(names:ArrayList<String>){
    var nameList:ArrayList<String>
    init{
        nameList=names
    }
    // 1. Using mutability and imperative style to compute the average number of letters in the names
    fun averageNameSize():Double{
        val listSize = nameList.size
        var totalLetters = 0
        for (name in nameList){
            totalLetters+=name.length
        }

        return totalLetters.toDouble() / listSize.toDouble()
    }

    //2. Using mutability and immutability, group the names based on the first letter.
    fun  namesByInitial(): ArrayList<ArrayList<String>>{
        val groupedNames = ArrayList<ArrayList<String>>()
        val initialsFound = ArrayList<Char>()
        var currentInitial:Char

        for (i in 0 until nameList.size) {
            val currentGroup = ArrayList<String>()
            if(!initialsFound.contains(nameList[i][0])){
                initialsFound.add(nameList[i][0])
                currentInitial = nameList[i][0]
                for (j in i until nameList.size) {
                    if (nameList[j][0]==currentInitial){
                        currentGroup.add(nameList[j])
                    }
                }
                if (currentGroup.size>0) {groupedNames.add(currentGroup)}
            }
        }
        return groupedNames
    }
    //3. Using mutability and imperative style, compute the number of names starting  with each letter.
    fun namesPerInitial(): ArrayList<ArrayList<String>>{
        val initialCounter = ArrayList<ArrayList<String>>()
        val initialsFound = ArrayList<Char>()
        var currentInitial:Char

        for (i in 0 until nameList.size) {
            var currentInstances = 0
            if(!initialsFound.contains(nameList[i][0])){
                initialsFound.add(nameList[i][0])
                currentInitial = nameList[i][0]
                for (j in i until nameList.size) {
                    if (nameList[j][0]==currentInitial){
                        currentInstances++
                    }
                }
                if (currentInstances>0) {initialCounter.add(arrayListOf(currentInitial.toString(), currentInstances.toString()))}
            }
        }
        return initialCounter
    }

    //4. Using full immutability and functional style, compute the average number of letters in the names.
    fun averageNameSizeImmutable(): Double{
        val listSize = nameList.size
        val totalLetters = nameList.map{it.length}.reduce{acc, size -> acc+size}

        return totalLetters.toDouble() / listSize.toDouble()
    }

    //5. Using full immutability and functional style, group the names based on the first letter.
    fun namesByInitialImmutable(): ArrayList<ArrayList<String>>  {

        val groups = HashMap<Char,ArrayList<String>>()

        fun checker(initial: Char, name:String) {
            if(!groups.containsKey(initial)){
                groups.put(initial, arrayListOf(name))
            }
            else{
                groups.getValue(initial).add(name)
            }
        }

        nameList.forEach{checker(it[0],it)}

        return ArrayList(groups.values)
    }

    //6. Using full immutability and functional style, compute the number of names starting with each letter.
    fun  namesPerInitialImmutable(): ArrayList<ArrayList<String>>
    {
        val initialCounter = HashMap<Char,Int>()

        fun adder(initial: Char) {
            if(initialCounter.containsKey(initial)){
                initialCounter.put(initial,initialCounter.getValue(initial)+1)
            }else{
                initialCounter.put(initial, 1)
            }
        }

        nameList.forEach{adder(it[0])}

        fun toList(key:Char):ArrayList<String>{
            return arrayListOf(key.toString() , initialCounter.getValue(key).toString())
        }

        return ArrayList(initialCounter.keys.map{toList(it)})
    }
}

fun main(){
    //val fileName = "names.txt"
    //val nameList:ArrayList<String> = ArrayList(File(fileName).readText().split(", "))

    val nameList = arrayListOf("Fadi", "Timothy", "Derrick", "Omar", "Hiep", "Alexander", "Daniel", "Isela", "Jackqueline", "Bryan", "Nguyen", "Maxim", "Edgar", "Ahson", "Abdulkadir", "En-Kai", "Karla", "Panpan", "You", "Andrew", "Trevor", "Hoang", "Johnson", "Janelle", "Moujie", "MD", "Albert", "Jiahui", "Alejandro", "Venkat")
    val nameProcessor = NameProcessor(nameList)
    // 1
    println("Average number of letters in names: %.2f".format(nameProcessor.averageNameSize()))
    // 2
    println("Names grouped based on first letter: "+nameProcessor.namesByInitial())
    // 3
    println("Number of names per initial: "+nameProcessor.namesPerInitial())
    // 4
    println("Average number of letters in names (full immutability): %.2f".format(nameProcessor.averageNameSizeImmutable()))
    // 5
    println("Names grouped based on first letter (full immutability): "+nameProcessor.namesByInitialImmutable())
    // 6
    println("Number of names per initial (full immutability): "+nameProcessor.namesPerInitialImmutable())

}