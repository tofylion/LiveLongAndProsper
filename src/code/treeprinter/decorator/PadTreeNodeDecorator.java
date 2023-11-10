package code.treeprinter.decorator;

import code.treeprinter.Insets;
import code.treeprinter.TreeNode;
import code.treeprinter.text.AnsiFormat;
import code.treeprinter.text.ConsoleText;
import code.treeprinter.text.PlainConsoleText;
import code.treeprinter.text.TextUtil;

/**
 * {@link TreeNode} decorator implementation that draws some padding around the node.
 * 
 * <p>Given the following content:</p>
 * 
 * <pre>
 * Hello, Node!
 * Line 2
 * </pre>
 * 
 * <p>With with <code>new Insets(1, 2, 3, 4)</code> and <code>~</code> as pad character</p>
 * 
 * <pre>
 * ~~~~~~~~~~~~~~~~~~
 * ~~~~Hello, Node!~~
 * ~~~~Line 2      ~~
 * ~~~~~~~~~~~~~~~~~~
 * ~~~~~~~~~~~~~~~~~~
 * ~~~~~~~~~~~~~~~~~~
 * </pre>
 */
public class PadTreeNodeDecorator extends AbstractTreeNodeDecorator {

    private final Insets insets;
    
    private final char padCharacter;
    
    private final AnsiFormat format;
    
    
    public PadTreeNodeDecorator(TreeNode baseNode) {
        this(baseNode, 1);
    }

    public PadTreeNodeDecorator(TreeNode baseNode, AnsiFormat format) {
        this(baseNode, 1, format);
    }

    public PadTreeNodeDecorator(TreeNode baseNode, int pad) {
        this(baseNode, builder().pad(pad));
    }

    public PadTreeNodeDecorator(TreeNode baseNode, int pad, AnsiFormat format) {
        this(baseNode, builder().pad(pad).format(format));
    }

    public PadTreeNodeDecorator(TreeNode baseNode, Insets insets) {
        this(baseNode, builder().insets(insets));
    }

    public PadTreeNodeDecorator(TreeNode baseNode, Insets insets, AnsiFormat format) {
        this(baseNode, builder().insets(insets).format(format));
    }

    private PadTreeNodeDecorator(TreeNode baseNode, Builder builder) {
        super(baseNode, builder.inherit, builder.decorable);
        this.insets = new Insets(builder.topPad, builder.rightPad, builder.bottomPad, builder.leftPad);
        this.padCharacter = builder.padCharacter;
        this.format = builder.format;
    }

    public static Builder builder() {
        return new Builder();
    }
    
    
    @Override
    public ConsoleText decoratedContent() {
        ConsoleText baseContent = baseNode.content();
        ConsoleText[] baseLines = TextUtil.linesOf(baseContent);
        int baseWidth = baseContent.dimensions().width();
        
        StringBuilder resultBuilder = new StringBuilder();
        appendTopPadding(resultBuilder, baseWidth);
        appendPaddedContentLines(resultBuilder, baseLines, baseWidth);
        appendBottomPadding(resultBuilder, baseWidth);
        
        String decoratedContent = resultBuilder.toString();
        boolean isPlain = (baseNode instanceof PlainConsoleText) && (format == AnsiFormat.NONE);
        return isPlain ? ConsoleText.of(decoratedContent) : ConsoleText.ofAnsi(decoratedContent);
    }
    
    private void appendTopPadding(StringBuilder stringBuilder, int width) {
        appendEmptyLines(stringBuilder, insets.top(), insets.left() + width + insets.right());
    }

    private void appendBottomPadding(StringBuilder stringBuilder, int width) {
        appendEmptyLines(stringBuilder, insets.bottom(), insets.left() + width + insets.right());
    }

    private void appendEmptyLines(StringBuilder stringBuilder, int n, int width) {
        for (int i = 0; i < n; i++) {
            stringBuilder.append(formattedPadding(width).ansi());
            stringBuilder.append('\n');
        }
    }

    private void appendPaddedContentLines(StringBuilder stringBuilder, ConsoleText[] lines, int width) {
        for (ConsoleText line: lines) {
            stringBuilder.append(formattedPadding(insets.left()).ansi());
            stringBuilder.append(line.ansi());
            TextUtil.repeat(stringBuilder, ' ', width - line.dimensions().width());
            stringBuilder.append(formattedPadding(insets.right()).ansi());
            stringBuilder.append('\n');
        }
    }
    
    private ConsoleText formattedPadding(int width) {
        return ConsoleText.of(TextUtil.repeat(padCharacter, width)).format(format);
    }

    @Override
    public Insets insets() {
        return baseNode.insets().extendedWith(insets);
    }
    
    @Override
    protected TreeNode wrapChild(TreeNode childNode, int index) {
        return builder()
                .decorable(decorable)
                .inherit(inherit)
                .insets(insets)
                .padCharacter(padCharacter)
                .format(format)
                .buildFor(childNode);
    }
    
    
    public static class Builder {
        
        private boolean inherit = true;

        private boolean decorable = true;

        private int topPad = 0;
        
        private int rightPad = 0;
        
        private int bottomPad = 0;
        
        private int leftPad = 0;
        
        private char padCharacter = ' ';
        
        private AnsiFormat format = AnsiFormat.NONE;
        

        public Builder inherit(boolean inherit) {
            this.inherit = inherit;
            return this;
        }

        public Builder decorable(boolean decorable) {
            this.decorable = decorable;
            return this;
        }

        public Builder pad(int pad) {
            return pad(pad, pad, pad, pad);
        }

        public Builder pad(int topPad, int rightPad, int bottomPad, int leftPad) {
            this.topPad = topPad;
            this.rightPad = rightPad;
            this.bottomPad = bottomPad;
            this.leftPad = leftPad;
            return this;
        }

        public Builder verticalPad(int verticalPad) {
            this.topPad = verticalPad;
            this.bottomPad = verticalPad;
            return this;
        }

        public Builder horizontalPad(int horizontalPad) {
            this.leftPad = horizontalPad;
            this.rightPad = horizontalPad;
            return this;
        }

        public Builder topPad(int topPad) {
            this.topPad = topPad;
            return this;
        }

        public Builder rightPad(int rightPad) {
            this.rightPad = rightPad;
            return this;
        }

        public Builder bottomPad(int bottomPad) {
            this.bottomPad = bottomPad;
            return this;
        }

        public Builder leftPad(int leftPad) {
            this.leftPad = leftPad;
            return this;
        }

        public Builder insets(Insets insets) {
            return pad(insets.top(), insets.right(), insets.bottom(), insets.left());
        }

        public Builder padCharacter(char padCharacter) {
            this.padCharacter = padCharacter;
            return this;
        }

        public Builder format(AnsiFormat format) {
            this.format = format;
            return this;
        }

        public PadTreeNodeDecorator buildFor(TreeNode node) {
            return new PadTreeNodeDecorator(node, this);
        }
        
    }
    
}
