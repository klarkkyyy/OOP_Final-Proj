# Image Format Issue - WebP Files

## Problem
Your image files (`warrior.jpg`, `mage.jpg`, `rogue.jpg`) are actually in **WebP format**, not JPG format. Java's built-in ImageIO library doesn't support WebP files, so they can't be loaded.

## Solution: Convert to JPG or PNG

You need to convert your WebP files to either JPG or PNG format. Here are several ways to do this:

### Option 1: Online Converter (Easiest)
1. Go to an online converter like:
   - https://cloudconvert.com/webp-to-jpg
   - https://convertio.co/webp-jpg/
   - https://ezgif.com/webp-to-jpg
2. Upload your `warrior.jpg`, `mage.jpg`, and `rogue.jpg` files
3. Convert them to JPG format
4. Download the converted files
5. Replace the original files in the project folder

### Option 2: Using Image Editing Software
- **GIMP** (Free): Open the file, File → Export As → Choose JPG
- **Paint.NET** (Free): Open file, File → Save As → Choose JPG
- **Photoshop**: Open file, File → Export → Save for Web → Choose JPG
- **Windows Paint**: Open file, File → Save As → JPEG Picture

### Option 3: Command Line (if you have ImageMagick installed)
```bash
magick warrior.jpg warrior_new.jpg
magick mage.jpg mage_new.jpg
magick rogue.jpg rogue_new.jpg
```

### Option 4: PowerShell (Windows)
```powershell
# This requires .NET and may not work for all WebP files
Add-Type -AssemblyName System.Drawing
$img = [System.Drawing.Image]::FromFile("warrior.jpg")
$img.Save("warrior_new.jpg", [System.Drawing.Imaging.ImageFormat]::Jpeg)
$img.Dispose()
```

## After Converting

1. Make sure the new files are named exactly:
   - `warrior.jpg`
   - `mage.jpg`
   - `rogue.jpg`

2. Place them in the project root folder (same location as `run.bat`)

3. Run the game again - the images should now load!

## Verify the Files

After converting, the files should:
- Start with bytes `FF D8` (JPG signature) or `89 50 4E 47` (PNG signature)
- Be readable by Java's ImageIO library
- Display correctly in the character selection screen

## Note

If you want to keep using WebP format, you would need to add a WebP library to your project (like `imageio-webp`), but converting to JPG/PNG is much simpler and works with the current code.

