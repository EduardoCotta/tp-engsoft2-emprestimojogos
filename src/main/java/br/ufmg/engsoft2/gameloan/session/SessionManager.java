package br.ufmg.engsoft2.gameloan.session;

public class SessionManager {
    private SessionManager() {
        throw new IllegalStateException("Utility class");
    }
    private static Session session;

    public static Session getSession(){
        if(session == null){
            session = new Session();
        }

        return session;
    }

    public static void clean(){
        session = null;
    }
}
