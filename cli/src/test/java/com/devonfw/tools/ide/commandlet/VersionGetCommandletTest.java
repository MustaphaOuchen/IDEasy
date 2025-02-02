package com.devonfw.tools.ide.commandlet;

import org.junit.jupiter.api.Test;

import com.devonfw.tools.ide.cli.CliException;
import com.devonfw.tools.ide.context.AbstractIdeContextTest;
import com.devonfw.tools.ide.context.IdeContext;
import com.devonfw.tools.ide.context.IdeTestContext;
import com.devonfw.tools.ide.log.IdeLogLevel;

/**
 * Integration test of {@link VersionGetCommandlet}.
 */
public class VersionGetCommandletTest extends AbstractIdeContextTest {

  /**
   * Test of {@link VersionGetCommandlet} run, when Installed Version is null.
   */
  @Test
  public void testVersionGetCommandletRunThrowsCliException() {

    // arrange
    String path = "workspaces/foo-test/my-git-repo";
    IdeContext context = newContext("basic", path, false);
    VersionGetCommandlet versionGet = context.getCommandletManager().getCommandlet(VersionGetCommandlet.class);
    versionGet.tool.setValueAsString("java", context);
    // act
    try {
      versionGet.run();
      failBecauseExceptionWasNotThrown(CliException.class);
    } catch (CliException e) {
      // assert
      assertThat(e).hasMessageContaining("Tool java is not installed!");
    }
  }

  /**
   * Test of {@link VersionGetCommandlet} run.
   */
  @Test
  public void testVersionGetCommandletRun() {

    // arrange
    String path = "workspaces/foo-test/my-git-repo";
    IdeTestContext context = newContext("basic", path, false);
    VersionGetCommandlet versionGet = context.getCommandletManager().getCommandlet(VersionGetCommandlet.class);
    // act
    versionGet.tool.setValueAsString("mvn", context);
    versionGet.run();
    // assert
    assertLogMessage(context, IdeLogLevel.INFO, "3.9.4");
  }
}
