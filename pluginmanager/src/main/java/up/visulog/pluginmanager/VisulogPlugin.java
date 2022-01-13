package up.visulog.pluginmanager;

import java.util.HashSet;
import java.util.Set;
import org.eclipse.jgit.revwalk.DepthWalk.Commit;
import org.eclipse.jgit.revwalk.RevCommit;

public abstract class VisulogPlugin implements Plugin {
    protected final Set<RevCommit> commits;

    /**
     * Instantiate a visulog plugin with given set of commits
     *
     * @param commits
     */
    public VisulogPlugin(Set<RevCommit> commits) {
        this.commits = commits;
    }

  /**
   * Creates a visulog plugin with empty set of commits
   */
  public VisulogPlugin() {
    this(new HashSet<>());
  }

    /**
     * Set commits with the given set of commits
     *
     * @param commits The new set of commits to add
     */
    public void setCommits(Set<RevCommit> commits) {
        this.commits.addAll(commits);
    }

  public abstract void run();
}
