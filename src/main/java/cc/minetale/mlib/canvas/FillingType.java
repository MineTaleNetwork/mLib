package cc.minetale.mlib.canvas;

public enum FillingType {

    BORDER {
        @Override
        public Fragment[] fillMenu(Menu menu) {
            var size = menu.getType().getSize();
            var fragments = new Fragment[size];

            for (int i = 0; i < size; i++) {
                if (i < 9 || i >= size - 9 || i % 9 == 0 || i % 9 == 8) {
                    if(fragments[i] == null) {
                        fragments[i] = new Fragment(menu.getFillerType(), event -> event.setCancelled(true));
                    }
                }
            }

            return fragments;
        }
    },

    EMPTY_SLOTS {
        @Override
        public Fragment[] fillMenu(Menu menu) {
            var size = menu.getType().getSize();
            var fragments = new Fragment[size];

            for (int i = 0; i < size; i++) {
                if(fragments[i] == null) {
                    fragments[i] = new Fragment(menu.getFillerType(), event -> event.setCancelled(true));
                }
            }

            return fragments;
        }
    },

    TOP_BOTTOM {
        @Override
        public Fragment[] fillMenu(Menu menu) {
            var size = menu.getType().getSize();
            var fragments = new Fragment[size];

            for (int i = 0; i < 9; i++) {
                if(fragments[i] == null) {
                    fragments[i] = new Fragment(menu.getFillerType(), event -> event.setCancelled(true));
                }
            }

            for (int i = size - 9; i < size; i++) {
                if(fragments[i] == null) {
                    fragments[i] = new Fragment(menu.getFillerType(), event -> event.setCancelled(true));
                }
            }

            return fragments;
        }
    };

    public abstract Fragment[] fillMenu(Menu menu);

}
