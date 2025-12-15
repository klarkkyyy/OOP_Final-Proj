package rpg;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;

/**
 * Utility class for loading character images
 */
public class ImageLoader {
    
    /**
     * Loads a character image from various possible locations
     * @param characterType The type of character (Warrior, Mage, Rogue)
     * @return ImageIcon if found, null otherwise
     */
    public static ImageIcon loadCharacterImage(String characterType) {
        String baseName = characterType.toLowerCase();
        String[] extensions = {".jpg", ".png", ".jpeg"};
        
        // Get current working directory - this should be the project root when run.bat is used
        String userDir = System.getProperty("user.dir");
        System.out.println("[ImageLoader] Looking for " + characterType + " image");
        System.out.println("[ImageLoader] Working directory: " + userDir);
        
        // Try to find project root from class file location
        String projectRoot = userDir;
        try {
            java.net.URL codeSource = ImageLoader.class.getProtectionDomain().getCodeSource().getLocation();
            if (codeSource != null) {
                String classPath = codeSource.getPath();
                // Handle URL encoding for Windows paths
                if (classPath.startsWith("/") && System.getProperty("os.name").toLowerCase().contains("win")) {
                    classPath = classPath.substring(1); // Remove leading slash on Windows
                }
                try {
                    classPath = java.net.URLDecoder.decode(classPath, "UTF-8");
                } catch (Exception e) {
                    // Ignore
                }
                
                File classFile = new File(classPath);
                if (classFile.exists()) {
                    // Navigate from bin/rpg/ImageLoader.class to project root
                    File current = classFile.isFile() ? classFile.getParentFile() : classFile;
                    // Go up: rpg -> bin -> project root
                    if (current != null && current.getName().equals("rpg")) {
                        File binDir = current.getParentFile();
                        if (binDir != null && binDir.getName().equals("bin")) {
                            File root = binDir.getParentFile();
                            if (root != null && root.exists()) {
                                projectRoot = root.getAbsolutePath();
                                System.out.println("[ImageLoader] Found project root from class: " + projectRoot);
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            System.out.println("[ImageLoader] Could not determine project root from class location");
        }
        
        // Try loading from project root (most likely location)
        for (String ext : extensions) {
            String fileName = baseName + ext;
            File imageFile = new File(projectRoot, fileName);
            String absolutePath = imageFile.getAbsolutePath();
            
            System.out.println("[ImageLoader] Trying: " + absolutePath);
            
            if (imageFile.exists() && imageFile.isFile()) {
                try {
                    System.out.println("[ImageLoader] File size: " + imageFile.length() + " bytes");
                    
                    // Try ImageIO with FileInputStream (more reliable)
                    BufferedImage bufferedImage = null;
                    try (java.io.FileInputStream fis = new java.io.FileInputStream(imageFile)) {
                        bufferedImage = ImageIO.read(fis);
                    } catch (Exception e) {
                        System.out.println("[ImageLoader] FileInputStream error: " + e.getMessage());
                        // Fallback to direct file read
                        bufferedImage = ImageIO.read(imageFile);
                    }
                    
                    if (bufferedImage != null) {
                        int width = bufferedImage.getWidth();
                        int height = bufferedImage.getHeight();
                        if (width > 0 && height > 0) {
                            // Scale the image
                            Image scaledImage = bufferedImage.getScaledInstance(150, 150, Image.SCALE_SMOOTH);
                            ImageIcon icon = new ImageIcon(scaledImage);
                            System.out.println("[ImageLoader] SUCCESS! Loaded " + characterType + " from: " + absolutePath + " (" + width + "x" + height + ")");
                            return icon;
                        } else {
                            System.out.println("[ImageLoader] Image has invalid dimensions: " + width + "x" + height);
                        }
                    } else {
                        // Check file signature to determine format
                        try (java.io.FileInputStream checkFis = new java.io.FileInputStream(imageFile)) {
                            byte[] header = new byte[4];
                            checkFis.read(header);
                            
                            // Check if it's WebP (RIFF format)
                            if (header[0] == 0x52 && header[1] == 0x49 && header[2] == 0x46 && header[3] == 0x46) {
                                System.out.println("[ImageLoader] ERROR: File is in WebP format, but Java ImageIO doesn't support WebP!");
                                System.out.println("[ImageLoader] SOLUTION: Convert " + fileName + " to JPG or PNG format.");
                                System.out.println("[ImageLoader] You can use online converters or image editing software.");
                            } else if ((header[0] & 0xFF) == 0xFF && (header[1] & 0xFF) == 0xD8) {
                                System.out.println("[ImageLoader] File appears to be JPG but ImageIO couldn't read it");
                            } else {
                                System.out.println("[ImageLoader] Unknown file format (signature: " + 
                                    String.format("%02X %02X %02X %02X", header[0]&0xFF, header[1]&0xFF, header[2]&0xFF, header[3]&0xFF) + ")");
                            }
                        } catch (Exception e3) {
                            System.out.println("[ImageLoader] Could not check file signature");
                        }
                        
                        // Try ImageIcon as fallback (sometimes works even when ImageIO doesn't)
                        System.out.println("[ImageLoader] Trying ImageIcon fallback...");
                        try {
                            ImageIcon icon = new ImageIcon(absolutePath);
                            if (icon.getIconWidth() > 0 && icon.getIconHeight() > 0) {
                                // Scale it
                                Image scaledImage = icon.getImage().getScaledInstance(150, 150, Image.SCALE_SMOOTH);
                                ImageIcon scaledIcon = new ImageIcon(scaledImage);
                                System.out.println("[ImageLoader] SUCCESS! Loaded " + characterType + " using ImageIcon fallback (" + icon.getIconWidth() + "x" + icon.getIconHeight() + ")");
                                return scaledIcon;
                            } else {
                                System.out.println("[ImageLoader] ImageIcon also has invalid dimensions");
                            }
                        } catch (Exception e2) {
                            System.out.println("[ImageLoader] ImageIcon fallback also failed: " + e2.getMessage());
                        }
                        
                        System.out.println("[ImageLoader] Supported formats: " + java.util.Arrays.toString(ImageIO.getReaderFormatNames()));
                    }
                } catch (Exception e) {
                    System.out.println("[ImageLoader] ERROR loading image: " + e.getMessage());
                    e.printStackTrace();
                }
            } else {
                System.out.println("[ImageLoader] File does not exist: " + absolutePath);
            }
        }
        
        // Also try current directory as fallback
        for (String ext : extensions) {
            String fileName = baseName + ext;
            File imageFile = new File(fileName);
            if (imageFile.exists() && imageFile.isFile()) {
                try {
                    BufferedImage bufferedImage = ImageIO.read(imageFile);
                    if (bufferedImage != null) {
                        int width = bufferedImage.getWidth();
                        int height = bufferedImage.getHeight();
                        if (width > 0 && height > 0) {
                            Image scaledImage = bufferedImage.getScaledInstance(150, 150, Image.SCALE_SMOOTH);
                            ImageIcon icon = new ImageIcon(scaledImage);
                            System.out.println("[ImageLoader] SUCCESS! Loaded " + characterType + " from current dir: " + imageFile.getAbsolutePath());
                            return icon;
                        }
                    }
                } catch (Exception e) {
                    System.out.println("[ImageLoader] Error in fallback: " + e.getMessage());
                }
            }
        }
        
        System.out.println("[ImageLoader] FAILED: Could not find image for " + characterType);
        return null;
    }
    
    /**
     * Creates a scaled ImageIcon from an existing ImageIcon
     */
    public static ImageIcon scaleImage(ImageIcon icon, int width, int height) {
        if (icon == null) return null;
        Image image = icon.getImage();
        Image scaledImage = image.getScaledInstance(width, height, Image.SCALE_SMOOTH);
        return new ImageIcon(scaledImage);
    }
}

