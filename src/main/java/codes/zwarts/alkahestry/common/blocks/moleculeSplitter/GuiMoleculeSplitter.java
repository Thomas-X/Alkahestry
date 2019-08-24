package codes.zwarts.alkahestry.common.blocks.moleculeSplitter;
import codes.zwarts.alkahestry.Alkahestry;
import codes.zwarts.alkahestry.ModBlocks;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.util.ResourceLocation;

public class GuiMoleculeSplitter extends GuiContainer {
    private Container container;
    private InventoryPlayer playerInv;
    private static final ResourceLocation BG_TEXTURE = new ResourceLocation(Alkahestry.MODID, "textures/gui/molecule_splitter.png");
    private boolean dirty = false;

    public GuiMoleculeSplitter(Container container, InventoryPlayer playerInv) {
        super(container);
        this.container = container;
        this.playerInv = playerInv;
        ((ContainerMoleculeSplitter)container)
                .moleculeSplitterTileEntity
                .addProgressBarHook(
                        () -> dirty = true
                );
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
        GlStateManager.color(1, 1, 1, 1);
        mc.getTextureManager().bindTexture(BG_TEXTURE);
        int x = (width - xSize) / 2;
        int y = (height - ySize) / 2;
        drawTexturedModalRect(x, y, 0, 0, xSize, ySize);
    }

    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        this.drawDefaultBackground();
        super.drawScreen(mouseX, mouseY, partialTicks);
        this.renderHoveredToolTip(mouseX, mouseY);
    }



    @Override
    public void updateScreen() {
        // if we're dirty means we should draw stuff on screen
        if (dirty) {
            Alkahestry.logger.info(((ContainerMoleculeSplitter) container).moleculeSplitterTileEntity.progress);
//            String name = String.valueOf(((ContainerMoleculeSplitter) container).moleculeSplitterTileEntity.progress);
//            fontRenderer.drawString(name, xSize / 2 - fontRenderer.getStringWidth(name) / 2, 6, 0x404040);
//            fontRenderer.drawString(playerInv.getDisplayName().getUnformattedText(), 8, ySize - 94, 0x404040);
            dirty = false;
        }
//        ((ContainerMoleculeSplitter) container)
//                .moleculeSplitterTileEntity
//                .
        super.updateScreen();
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
//        String name = I18n.format(ModBlocks.blocks.get(ModBlocks.MOLECULE_SPLITTER).getUnlocalizedName() + ".name");
    }
}