package svn.defaultpackage;/*
 * 获取提交版本时的信息，包括版本号，提交者，日期，注释，变更的文件,相当于"svn log"
 * 但是，无法获取使用VisualSVN Server创建的版本库的信息
 */
import java.util.Collection;
import java.util.Iterator;
import java.util.Set;

import org.tmatesoft.svn.core.SVNException;
import org.tmatesoft.svn.core.SVNLogEntry;
import org.tmatesoft.svn.core.SVNLogEntryPath;
import org.tmatesoft.svn.core.SVNURL;
import org.tmatesoft.svn.core.auth.ISVNAuthenticationManager;
import org.tmatesoft.svn.core.internal.io.dav.DAVRepositoryFactory;
import org.tmatesoft.svn.core.io.SVNRepository;
import org.tmatesoft.svn.core.wc.SVNWCUtil;

public class History {
	static String username="admin";
	static String password="admin";
	static String svnRepositoryURL="svn://localhost/SVNKitRepository3";
	static long startRevision = 0;
    static long endRevision = -1;//HEAD (the latest) revision
    public static SVNRepository repository=null;
    @SuppressWarnings("deprecation")
	public static void main(String[] args) {
    	DAVRepositoryFactory.setup();

        try {							
			@SuppressWarnings("deprecation")
			SVNURL svnUrl=SVNURL.parseURIDecoded(svnRepositoryURL);
			repository = DAVRepositoryFactory.create(svnUrl);
			ISVNAuthenticationManager authManager =SVNWCUtil.createDefaultAuthenticationManager(username, password);
			repository.setAuthenticationManager(authManager);
		} catch (Exception e) {
			System.out.println("获取与版本库的连接时：出错-->");
			e.printStackTrace();
		}
        try {
			System.out.println( "Repository Root: " + repository.getRepositoryRoot( true ) );
		} catch (SVNException e) {
			System.out.println("respository Root失败");
			e.printStackTrace();
		}
        try {
            endRevision = repository.getLatestRevision();
        } catch (SVNException svne) {
            System.err.println("error while fetching the latest repository revision: " + svne.getMessage());
            System.exit(1);
        }

        Collection logEntries = null;
        try {
            /*
             * Collects SVNLogEntry objects for all revisions in the range
             * defined by its start and end points [startRevision, endRevision].
             * For each revision commit information is represented by
             * SVNLogEntry.
             * 
             * the 1st parameter (targetPaths - an array of path strings) is set
             * when restricting the [startRevision, endRevision] range to only
             * those revisions when the paths in targetPaths were changed.
             * 
             * the 2nd parameter if non-null - is a user's Collection that will
             * be filled up with found SVNLogEntry objects; it's just another
             * way to reach the scope.
             * 
             * startRevision, endRevision - to define a range of revisions you are
             * interested in; by default in this program - startRevision=0, endRevision=
             * the latest (HEAD) revision of the repository.
             * 
             * the 5th parameter - a boolean flag changedPath - if true then for
             * each revision a corresponding SVNLogEntry will contain a map of
             * all paths which were changed in that revision.
             * 
             * the 6th parameter - a boolean flag strictNode - if false and a
             * changed path is a copy (branch) of an existing one in the repository
             * then the history for its origin will be traversed; it means the 
             * history of changes of the target URL (and all that there's in that 
             * URL) will include the history of the origin path(s).
             * Otherwise if strictNode is true then the origin path history won't be
             * included.
             * 
             * The return value is a Collection filled up with SVNLogEntry Objects.
             */
            logEntries = repository.log(new String[] {""}, null,
                    startRevision, endRevision, true, true);

        } catch (SVNException svne) {
            System.out.println("error while collecting log information for '"
                    + svnRepositoryURL + "': " + svne.getMessage());
            System.exit(1);
        }
        for (Iterator entries = logEntries.iterator(); entries.hasNext();) {
            /*
             * gets a next SVNLogEntry
             */
            SVNLogEntry logEntry = (SVNLogEntry) entries.next();
            System.out.println("---------------------------------------------");
            /*
             * gets the revision number
             */
            System.out.println("revision: " + logEntry.getRevision());
            /*
             * gets the author of the changes made in that revision
             */
            System.out.println("author: " + logEntry.getAuthor());
            /*
             * gets the time moment when the changes were committed
             */
            System.out.println("date: " + logEntry.getDate().toLocaleString());
            /*
             * gets the commit log message
             */
            System.out.println("log message: " + logEntry.getMessage());
            /*
             * displaying all paths that were changed in that revision; cahnged
             * path information is represented by SVNLogEntryPath.
             */
            if (logEntry.getChangedPaths().size() > 0) {
                System.out.println();
                System.out.println("changed paths:");
                /*
                 * keys are changed paths
                 */
                Set changedPathsSet = logEntry.getChangedPaths().keySet();

                for (Iterator changedPaths = changedPathsSet.iterator(); changedPaths
                        .hasNext();) {
                    /*
                     * obtains a next SVNLogEntryPath
                     */
                    SVNLogEntryPath entryPath = (SVNLogEntryPath) logEntry
                            .getChangedPaths().get(changedPaths.next());
                    /*
                     * SVNLogEntryPath.getPath returns the changed path itself;
                     * 
                     * SVNLogEntryPath.getType returns a charecter describing
                     * how the path was changed ('A' - added, 'D' - deleted or
                     * 'M' - modified);
                     * 
                     * If the path was copied from another one (branched) then
                     * SVNLogEntryPath.getCopyPath &
                     * SVNLogEntryPath.getCopyRevision tells where it was copied
                     * from and what revision the origin path was at.
                     */
                    System.out.println(" "
                            + entryPath.getType()
                            + "	"
                            + entryPath.getPath()
                            + ((entryPath.getCopyPath() != null) ? " (from "
                                    + entryPath.getCopyPath() + " revision "
                                    + entryPath.getCopyRevision() + ")" : ""));
                }
            }
        }
    }
}
/*
User's authentication information (name/password) is provided via  an 
ISVNAuthenticationManager  instance.  SVNWCUtil  creates  a   default 
authentication manager given user's name and password.

Default authentication manager first attempts to use provided user name 
and password and then falls back to the credentials stored in the 
default Subversion credentials storage that is located in Subversion 
configuration area. If you'd like to use provided user name and password 
only you may use BasicAuthenticationManager class instead of default 
authentication manager:

BasicAuthenticationManager authManager0 = new BasicAuthenticationManager(username, password);

ISVNAuthenticationManager authManager = SVNWCUtil.createDefaultAuthenticationManager(name, password);
repository.setAuthenticationManager(authManager);

try {
  repository = SVNRepositoryFactory.create(SVNURL.parseURIEncoded(url));
} catch (SVNException svne) {
  System.err.println("error while creating an SVNRepository for the location '"+ url + "': " + svne.getMessage());
  System.exit(1);
}
*/