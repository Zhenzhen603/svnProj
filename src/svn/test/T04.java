package svn.test;

import java.io.File;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;

import org.tmatesoft.svn.core.SVNDirEntry;
import org.tmatesoft.svn.core.SVNException;
import org.tmatesoft.svn.core.SVNNodeKind;
import org.tmatesoft.svn.core.io.SVNRepository;

public class T04 {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}
	/** 
     * 非递归遍历 
     * @param file 
     * @return 
     */  
    public static LinkedList<File> GetDirectory(String path) {  
        File file = new File(path);  
        LinkedList<File> Dirlist = new LinkedList<File>(); // 保存待遍历文件夹的列表  
        LinkedList<File> fileList = new LinkedList<File>();  
        GetOneDir(file, Dirlist, fileList);// 调用遍历文件夹根目录文件的方法  
        File tmp;  
        while (!Dirlist.isEmpty()) {  
            tmp = (File) Dirlist.removeFirst();// 从文件夹列表中删除第一个文件夹，并返回该文件夹赋给tmp变量  
            // 遍历这个文件夹下的所有文件，并把  
            GetOneDir(tmp, Dirlist, fileList);  
  
        }  
        return fileList;  
    }  
  
    // 遍历指定文件夹根目录下的文件  
    private static void GetOneDir(File file, LinkedList<File> Dirlist,  
            LinkedList<File> fileList) {  
        // 每个文件夹遍历都会调用该方法  
        File[] files = file.listFiles();  
  
        if (files == null || files.length == 0) {  
            return;  
        }  
        for (File f : files) {  
            if (f.isDirectory()) {  
                Dirlist.add(f);  
            } else {  
                // 这里列出当前文件夹根目录下的所有文件,并添加到fileList列表中  
                fileList.add(f);  
                // System.out.println("file==>" + f);  
  
            }  
        }  
    }  

    public static void listEntries(SVNRepository repository, String path)
            throws SVNException {
        Collection entries = repository.getDir(path, -1, null,
                (Collection) null);
        Iterator iterator = entries.iterator();
        while (iterator.hasNext()) {
            SVNDirEntry entry = (SVNDirEntry) iterator.next();
            System.out.println("/" + (path.equals("") ? "" : path + "/")
                    + entry.getName() + " (author: '" + entry.getAuthor()
                    + "'; revision: " + entry.getRevision() + "; date: " + entry.getDate() + ")");
            /*
             * Checking up if the entry is a directory.
             */
            if (entry.getKind() == SVNNodeKind.DIR) {
                listEntries(repository, (path.equals("")) ? entry.getName()
                        : path + "/" + entry.getName());
            }
        }
    }
}
