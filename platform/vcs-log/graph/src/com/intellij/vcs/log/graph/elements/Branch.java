package com.intellij.vcs.log.graph.elements;

import com.intellij.openapi.util.Condition;
import com.intellij.util.containers.ContainerUtil;
import com.intellij.vcs.log.VcsRef;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;

/**
 * @author erokhins
 */
public final class Branch {
  private final int upCommitHash;
  private final int downCommitHash;
  @Nullable private final VcsRef myColoredRef;
  private final int myOneOfHeads;

  public Branch(int upCommitHash, int downCommitHash, @NotNull Collection<VcsRef> refs, int oneOfHeads) {
    this.upCommitHash = upCommitHash;
    this.downCommitHash = downCommitHash;
    myOneOfHeads = oneOfHeads;
    myColoredRef = findRefForBranchColor(refs);
  }

  @Nullable
  private static VcsRef findRefForBranchColor(@NotNull Collection<VcsRef> refs) {
    return ContainerUtil.find(refs, new Condition<VcsRef>() {
      @Override
      public boolean value(VcsRef ref) {
        return ref.getType().isBranch();
      }
    });
  }

  public Branch(int commit, @NotNull Collection<VcsRef> refs, int oneOfHeads) {
    this(commit, commit, refs, oneOfHeads);
  }

  public int getUpCommitHash() {
    return upCommitHash;
  }

  public int getDownCommitHash() {
    return downCommitHash;
  }

  public int getBranchNumber() {
    if (myColoredRef == null) {
      return upCommitHash + 73 * downCommitHash;
    }
    return myColoredRef.getName().hashCode();
  }

  @Override
  public int hashCode() {
    return upCommitHash + 73 * downCommitHash;
  }

  @Override
  public boolean equals(Object obj) {
    if (obj != null && obj.getClass() == Branch.class) {
      Branch anBranch = (Branch)obj;
      return anBranch.upCommitHash == upCommitHash && anBranch.downCommitHash == downCommitHash;
    }
    return false;
  }

  @Override
  public String toString() {
    if (upCommitHash == downCommitHash) {
      return String.valueOf(upCommitHash);
    }
    else {
      return String.valueOf(upCommitHash) + '#' + String.valueOf(downCommitHash);
    }
  }

  public int getOneOfHeads() {
    return myOneOfHeads;
  }

}
