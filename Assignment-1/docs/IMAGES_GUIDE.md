# Images Setup Guide

## Adding Screenshots to README

To display your console output screenshots in the README, follow these steps:

### Step 1: Save Your Screenshots

Save your two console output screenshots with these names:
- `output-part1.png` - First screenshot (Total Revenue through Top Customers)
- `output-part2.png` - Second screenshot (Monthly Revenue and Data Quality Checks)

### Step 2: Place Images in Correct Location

Copy your screenshot files to:
```
Assignment-1/docs/images/
```

So your directory structure should look like:
```
Assignment-1/
├── docs/
│   └── images/
│       ├── output-part1.png
│       └── output-part2.png
├── README.md
└── ... other files
```

### Step 3: Verify

The README.md already references these images:
```markdown
![Sample Output - Part 1](docs/images/output-part1.png)
![Sample Output - Part 2](docs/images/output-part2.png)
```

Once you place the images in the correct location, they will automatically display when viewing the README on GitHub or in your IDE.

## Alternative: Using Relative Paths

If your screenshots are in a different location, you can update the image paths in README.md:

```markdown
![Sample Output - Part 1](path/to/your/screenshot1.png)
![Sample Output - Part 2](path/to/your/screenshot2.png)
```

## Image Requirements

- **Format**: PNG, JPG, or GIF
- **Recommended size**: 1200px wide or less for optimal GitHub display
- **File naming**: Use descriptive names without spaces (use hyphens instead)

## Testing Locally

To preview how images will look:
1. Save images in `docs/images/` folder
2. Open README.md in your IDE (VS Code, IntelliJ, etc.)
3. Use preview mode to see the rendered markdown with images

## For GitHub

Once you push to GitHub, the images will display automatically if:
- Images are committed to the repository
- Paths in README.md are correct relative to the repository root
- Images are in a supported format (PNG, JPG, GIF)

---

**Current Image Paths in README:**
- `docs/images/output-part1.png`
- `docs/images/output-part2.png`

**Action Required:** 
Copy your two screenshot files to the `docs/images/` folder with the names above.

