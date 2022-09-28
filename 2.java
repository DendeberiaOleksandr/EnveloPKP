private boolean search(int[] numbers, int x){
    for(int number: numbers){
        if(number == x){
            return true;
        }
    }
    return false;
}