import mill._
import ammonite.ops._


trait FontDownloader extends Module {

  def getFonts = T {
    val dest = T.ctx().dest
    val fontsDir = dest / 'fonts
    mkdir(fontsDir)
    mill.modules.Util.downloadUnpackZip("https://github.com/adobe-fonts/source-serif-pro/releases/download/3.001R/source-serif-pro-3.001R.zip",
      "source-serif-pro-3.001R")

    os.copy(dest/"source-serif-pro-3.001R", fontsDir, replaceExisting = true)
  }
}
