package my.snippets;


public final class Chronometer{
    private long begin;
    private long end = 0;
	private boolean isRunning;
	private long currentTime;
	private Thread t;
	private long until;
  
    public void start() {
    	setRunning(true);
    	begin = System.currentTimeMillis();
    }
            
    public void stop() {
    	end = System.currentTimeMillis();
    	setRunning(false);
    }
    
	private void restart() {
		t = null;
		System.out.println("RESTARTING UNTIL: "+until);
		runUntil(this.until);
	}
    
    public void runUntil(final long until) {
     	this.until = until;
    	t = new Thread(new Runnable() {
			
			public void run() {
				System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>> START");
				start();						
				for(long i = 0; i < until; i++) {
		    		try {
		    			long current = System.currentTimeMillis();
		    	    	setCurrentTime(current - begin);
		    	    	System.out.println("i = "+i+" CURR: "+getCurrentTime());
		    	    	Thread.sleep(1);
		    		} catch (InterruptedException e) {
		    			e.printStackTrace();
		    		}
		    	}   	
				
				System.out.println(">>>>>>>>>>>>>>>>>>>>>>> STOP");
		    	stop();		    	
		    }
		});
    	
    	t.start();
    }
    
    protected void setCurrentTime(long l) {
		this.currentTime = l;
		
	}

	public long getCurrentTime(){
 		return currentTime;
    }
    
	public void setRunning(boolean isRunning) {
		this.isRunning = isRunning;
	}
	
    protected boolean isRunning() {
    	return isRunning;
    }
    
    public long getTime() {
    	long time = end-begin;
    	return time;
    }
    
    public static void main(String[] args) throws InterruptedException {
    	
    	Chronometer ch = new Chronometer();
    	
    	ch.runUntil(20);
    	
    	//Da um tempo para o run acima iniciar
    	Thread.sleep(2);
    	
    	System.out.println("IsRunning: "+ch.isRunning());
    	int timesReseted = 0;
    	while(ch.isRunning()) {
    		//Thread.sleep(1);
    		long currentTime2 = ch.getCurrentTime();
    		if(timesReseted == 0 && currentTime2 == 5) {
    			//System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> RESTARTING");
    			timesReseted += 1;
    			ch.restart();
    		}
    		
    		//System.out.println("Aguardando: "+ch.getCurrentTime());
    	}
    	
    	System.out.println("AGUARDEI: "+ch.getCurrentTime());
    	
    }
    
}