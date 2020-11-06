unsigned powerHelper(unsigned x, unsigned y, unsigned ans){
	if(y==0){
		return 1;
	} else if(y==1){
		return ans*x;
	}
	else{
		return powerHelper(x,y-1,ans*x);
	}
}

unsigned power(unsigned x, unsigned y){
	return powerHelper(x,y,1);
} 