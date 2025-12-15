# Adding Character Images

## Yes, you should add the images!

The game now supports character images. If you add `warrior.jpg`, `mage.jpg`, and `rogue.jpg`, they will automatically be displayed instead of the emoji icons.

## Where to Place Images

Place the image files in the **project root directory** (same folder as `run.bat`):

```
OOP_Final-Proj/
├── warrior.jpg    ← Place here
├── mage.jpg       ← Place here
├── rogue.jpg      ← Place here
├── run.bat
├── src/
└── bin/
```

## Image Requirements

- **Format**: JPG or PNG (JPG preferred)
- **Recommended Size**: 256x256 pixels or larger (square images work best)
- **Aspect Ratio**: Square (1:1) is ideal, but any ratio will work
- **File Names**: Must be exactly:
  - `warrior.jpg` (or `warrior.png`)
  - `mage.jpg` (or `mage.png`)
  - `rogue.jpg` (or `rogue.png`)

## How It Works

The game will:
1. Try to load images from multiple possible locations (current directory, parent directory, user directory)
2. Supports both `.jpg` and `.png` formats
3. If images are found, they will be displayed (scaled to 150x150 pixels)
4. If images are not found, it will fall back to emoji icons (⚔️ 🔮 🗡️)

## Troubleshooting

If images don't show:

1. **Check the console output** - The game prints where it's looking for images
   - Look for messages like "Loaded image from: [path]"
   - Or "Image not found for: [character]"

2. **Verify file location** - Make sure images are in the project root:
   ```
   OOP_Final-Proj/
   ├── warrior.jpg  ← Should be here
   ├── mage.jpg     ← Should be here
   ├── rogue.jpg    ← Should be here
   ```

3. **Check file names** - Must be exactly:
   - `warrior.jpg` (not `Warrior.jpg` or `WARRIOR.JPG`)
   - `mage.jpg`
   - `rogue.jpg`

4. **Run from correct directory** - Use `run.bat` which ensures correct directory

5. **Check file format** - Images should be valid JPG or PNG files

## Example

If you have:
- `warrior.jpg` in the project root → Warrior card shows the image
- `mage.jpg` missing → Mage card shows 🔮 emoji
- `rogue.jpg` in the project root → Rogue card shows the image

## Tips

- Use clear, front-facing character portraits
- Ensure good contrast so characters are visible on white background
- Images will be automatically scaled, so high resolution is fine
- The game supports both `.jpg` and `.png` formats

