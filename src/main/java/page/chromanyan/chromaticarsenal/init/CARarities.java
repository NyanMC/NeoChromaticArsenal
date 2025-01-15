package page.chromanyan.chromaticarsenal.init;

import page.chromanyan.chromaticarsenal.ChromaticArsenal;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Style;
import net.minecraft.world.item.Rarity;
import net.neoforged.fml.common.asm.enumextension.EnumProxy;

import java.util.function.UnaryOperator;

@SuppressWarnings("unused")
public class CARarities {
    public static final String CHALLENGE = "CHROMATICARSENAL_CHALLENGE";

    public static final EnumProxy<Rarity> CHALLENGE_ENUM_PROXY = new EnumProxy<>(
            Rarity.class, -1, ChromaticArsenal.of("challenge").toString(), (UnaryOperator<Style>) style -> style.withColor(ChatFormatting.DARK_RED)
    );
}
