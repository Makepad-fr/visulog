package up.visulog.git;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import org.eclipse.jgit.revwalk.DepthWalk.Commit;

public class CommitExtractor {
  private final String[] authors;
  private final Date startDate, endDate;
  private final String path, branchName;

  /**
   * Creates a new commit extractor instance with the given paramaeters
   * @param authors An array of authors to extract commits with
   * @param startDate The start extract commits starting from
   * @param endDate The end date to extract commits end at
   * @param path The path of the git repository
   * @param branchName The name of the git branch
   */
  public CommitExtractor(String[] authors, Date startDate, Date endDate, String path, String branchName) {
    this.authors = authors;
    this.startDate = startDate;
    this.endDate = endDate;
    this.path = path;
    this.branchName = branchName;
  }

  /**
   * Get the set of the extracted commits
   * @return The set of extracted commits matching the parameters
   */
  public Set<Commit> extract() {
    // TODO: Complete method implementation
    return new HashSet<>();
  }
}
