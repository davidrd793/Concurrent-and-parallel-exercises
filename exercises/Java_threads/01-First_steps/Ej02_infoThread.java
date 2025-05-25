/*El objetivo de este código es hacerse una idea de toda la información que tiene internamente un Thread, y como acceder a ella (mediante métodos get) */

class Ej02_infoThread {
    public static void main(String[] args) {
        printThreadDetails();
    }
    private static void printThreadDetails() {
        Thread currentThread = Thread.currentThread();
        long id          = currentThread.threadId();
        String name      = currentThread.getName();
        int priority     = currentThread.getPriority();
        Thread.State state     = currentThread.getState();
        String threadGroupName = currentThread.getThreadGroup().getName();
        boolean isDaemon       = currentThread.isDaemon();
        int activeThreadCount  = currentThread.getThreadGroup().activeCount();

        System.out.printf("ID: %d | Name: %s | Priority: %d | State: %s | Thread Group Name: %s | Is Daemon: %s | Active Thread Count in Group: %d%n",
                id, name, priority, state, threadGroupName, isDaemon, activeThreadCount);
    }
}

/*Un aspecto a tener en cuenta es el hecho de que no es necesario hacer una clase trabajadora Thread para lanzarla y ver su información, pues 
 * la ejecución de un programa cualquiera ocurre en un Thread (master Thread o main Thread)
*/
