package io.ipfs.multibase;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/*
 * Copyright 2025 Michael Vorburger.ch
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

/**
 * <a href="https://github.com/multiformats/multibase/blob/master/rfcs/Base256Emoji.md">Base256Emoji</a>
 * is an encoding mapping each 0-255 byte value to (or from) a specific single Unicode Emoji character.
 *
 * @author <a href="https://www.vorburger.ch/">Michael Vorburger.ch</a>
 */
public class Base256Emoji {

    // from https://github.com/multiformats/multibase/blob/master/rfcs/Base256Emoji.md
    private static final String[] EMOJIS = {
            "🚀", "🪐", "☄", "🛰", "🌌", "🌑", "🌒", "🌓", "🌔", "🌕",
            "🌖", "🌗", "🌘", "🌍", "🌏", "🌎", "🐉", "☀", "💻", "🖥",
            "💾", "💿", "😂", "❤", "😍", "🤣", "😊", "🙏", "💕", "😭",
            "😘", "👍", "😅", "👏", "😁", "🔥", "🥰", "💔", "💖", "💙",
            "😢", "🤔", "😆", "🙄", "💪", "😉", "☺", "👌", "🤗", "💜",
            "😔", "😎", "😇", "🌹", "🤦", "🎉", "💞", "✌", "✨", "🤷",
            "😱", "😌", "🌸", "🙌", "😋", "💗", "💚", "😏", "💛", "🙂",
            "💓", "🤩", "😄", "😀", "🖤", "😃", "💯", "🙈", "👇", "🎶",
            "😒", "🤭", "❣", "😜", "💋", "👀", "😪", "😑", "💥", "🙋",
            "😞", "😩", "😡", "🤪", "👊", "🥳", "😥", "🤤", "👉", "💃",
            "😳", "✋", "😚", "😝", "😴", "🌟", "😬", "🙃", "🍀", "🌷",
            "😻", "😓", "⭐", "✅", "🥺", "🌈", "😈", "🤘", "💦", "✔",
            "😣", "🏃", "💐", "☹", "🎊", "💘", "😠", "☝", "😕", "🌺",
            "🎂", "🌻", "😐", "🖕", "💝", "🙊", "😹", "🗣", "💫", "💀",
            "👑", "🎵", "🤞", "😛", "🔴", "😤", "🌼", "😫", "⚽", "🤙",
            "☕", "🏆", "🤫", "👈", "😮", "🙆", "🍻", "🍃", "🐶", "💁",
            "😲", "🌿", "🧡", "🎁", "⚡", "🌞", "🎈", "❌", "✊", "👋",
            "😰", "🤨", "😶", "🤝", "🚶", "💰", "🍓", "💢", "🤟", "🙁",
            "🚨", "💨", "🤬", "✈", "🎀", "🍺", "🤓", "😙", "💟", "🌱",
            "😖", "👶", "🥴", "▶", "➡", "❓", "💎", "💸", "⬇", "😨",
            "🌚", "🦋", "😷", "🕺", "⚠", "🙅", "😟", "😵", "👎", "🤲",
            "🤠", "🤧", "📌", "🔵", "💅", "🧐", "🐾", "🍒", "😗", "🤑",
            "🌊", "🤯", "🐷", "☎", "💧", "😯", "💆", "👆", "🎤", "🙇",
            "🍑", "❄", "🌴", "💣", "🐸", "💌", "📍", "🥀", "🤢", "👅",
            "💡", "💩", "👐", "📸", "👻", "🤐", "🤮", "🎼", "🥵", "🚩",
            "🍎", "🍊", "👼", "💍", "📣", "🥂" };

    // TODO Propose adding a Guava dependency to use ImmutableMap instead of this

    private static final Map<String, Integer> EMOJI_TO_INDEX;
    private static final int MAP_EXPECTED_SIZE = EMOJIS.length;
    private static final float MAP_LOAD_FACTOR = 1.0f;

    static {
        if (EMOJIS.length != 256) {
            throw new IllegalStateException("EMOJIS.length must be 256, but is " + EMOJIS.length);
        }

        Map<String, Integer> mutableMap = new HashMap<>(MAP_EXPECTED_SIZE, MAP_LOAD_FACTOR);
        for (int i = 0; i < EMOJIS.length; i++) {
            mutableMap.put(EMOJIS[i], i);
        }
        EMOJI_TO_INDEX = Collections.unmodifiableMap(mutableMap);
    }

    public static String encode(byte[] in) {
        StringBuilder sb = new StringBuilder(in.length);
        for (byte b : in) {
            sb.append(EMOJIS[b & 0xFF]);
        }
        return sb.toString();
    }

    public static byte[] decode(String in) {
        int length = in.codePointCount(0, in.length());
        byte[] bytes = new byte[length];

        for (int i = 0; i < in.codePointCount(0, in.length()); i++) {
            int cp = in.codePointAt(in.offsetByCodePoints(0, i));
            String emoji = new String(Character.toChars(cp));
            Integer index = EMOJI_TO_INDEX.get(emoji);
            if (index == null) {
                throw new IllegalArgumentException("Unknown Base256Emoji character: " + emoji);
            }
            bytes[i] = (byte) (index & 0xFF);
        }

        return bytes;
    }

}
