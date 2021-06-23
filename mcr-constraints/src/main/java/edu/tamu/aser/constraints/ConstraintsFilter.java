package edu.tamu.aser.constraints;

import javafx.util.Pair;

import java.util.*;

public class ConstraintsFilter {
    /**
     * @param CONS_ASSERT_PO    程序顺序性约束
     * @param CONS_ASSERT_VALID 锁相关约束
     * @param causalConstraint  新的读写约束
     */
    public static long totalConstraintsCounts = 0;
    public static long filtedConstraintsCounts = 0;


    public Pair<Boolean, StringBuilder> doFilter_with_expression(StringBuilder CONS_ASSERT_PO, StringBuilder CONS_ASSERT_VALID, StringBuilder causalConstraint) {
//        Map<String,Integer> orderMap = new HashMap<>();
//        System.out.println("程序基本约束："+ CONS_ASSERT_PO.append(CONS_ASSERT_VALID).toString().replaceAll("\n",""));
//        System.out.println("读写约束："+ causalConstraint.toString().replaceAll("\n",""));

        try {
            StringBuilder result = new StringBuilder("");
            List<String[]> variables = new ArrayList<>();
            variables.addAll(findVariable(CONS_ASSERT_PO.toString()));
            variables.addAll(findVariable(CONS_ASSERT_VALID.toString()));
            //构建邻接表
            List<OrderNode> orderMapList = createOrderMap(variables);
            //读写约束处理
            StringBuilder sb = new StringBuilder();
            for (char c : causalConstraint.toString().toCharArray()) {
                if (c != '\n') {
                    if (c == '(' || c == ')') {
                        sb.append(" ");
                    } else {
                        sb.append(c);
                    }
                }
            }
//        System.out.println(sb);


            String[] asserts = sb.toString().trim().split("assert");
            List<String> assertsString = new ArrayList<>();
            for (String anAssert : asserts) {
                if (!anAssert.equals("\\s+")) {
                    assertsString.add(anAssert);
                }
            }

            //遍历寻找路径是否存在
            for (String anAssert : assertsString) {
                Queue<Pair<String, String>> rebuildCausalConstraint = rebuildExpression_with_expression(anAssert, orderMapList);

                if (!rebuildCausalConstraint.isEmpty()) {
                    Stack<Pair<String, String>> statck = new Stack<>();
                    while (!rebuildCausalConstraint.isEmpty()) {
                        Pair<String, String> var1 = rebuildCausalConstraint.poll();
                        while (!statck.isEmpty() && ("true".equals(statck.peek().getKey()) || "false".equals(statck.peek().getKey())) && ("true".equals(var1.getKey()) || "false".equals(var1.getKey()))) {
                            Pair<String, String> var2 = statck.pop();
                            Pair<String, String> option = statck.pop();

                            boolean var1Value = "true".equals(var1.getKey());
                            boolean var2Value = "true".equals(var2.getKey());
                            boolean finalValue;
                            String expression = "";

                            if ("and".equals(option.getKey())) {
                                finalValue = var1Value && var2Value;

                                if (finalValue) expression = "(and " + var1.getValue() + " " + var2.getValue() + " )";
                            } else {
                                finalValue = var1Value || var2Value;

                                if (finalValue) {
                                    if (var1Value && var2Value) expression = "(or " + var1.getValue() + " " + var2.getValue() + " )";
                                    else if (var1Value) expression = var1.getValue();
                                    else if (var2Value) expression = var2.getValue();
                                }
                            }
                            var1 = new Pair<>(String.valueOf(finalValue), expression);
                        }
                        statck.push(var1);
                    }

                    if ("false".equals(statck.get(0).getKey())) {
                        return new Pair<>(false, result);
                    } else {
                        result.append("(assert " + statck.get(0).getValue() + ")\n");
                    }
                }
            }

            return new Pair<>(true, result);
        } catch (Error error) {
            System.out.println(CONS_ASSERT_PO.append("================").append(CONS_ASSERT_VALID));
            throw error;
        }
    }

    public List<String[]> findVariable(String s) {
        List<String[]> result = new ArrayList<>();
        if (s.contains("or")) {
//            System.out.println("基本约束中存在or");
            return result;
        }
        StringBuilder sb = new StringBuilder();
        for (char c : s.toCharArray()) {
            if (c != '\n') {
                sb.append(c);
                if (c == '(' || c == ')') {
                    sb.append(" ");
                }
            }
        }
//        System.out.println(sb);
//        System.out.println("===============");
        String[] s1 = sb.toString().split("\\s+");
        for (int i = 0; i < s1.length; i++) {
            String s2 = s1[i];
            if ("<".equals(s2) || ">".equals(s2)) {
                String var1 = s1[i + 1];
                String var2 = s1[i + 2];
                result.add(new String[]{var1, var2, s2});
                i += 2;
            }
        }
        return result;
    }


    public Queue<Pair<String, String>> rebuildExpression_with_expression(String anAssert, List<OrderNode> orderMapList) {
        Queue<Pair<String, String>> queue = new LinkedList<>();
        StringBuilder sb = new StringBuilder();

        String[] s1 = anAssert.split("\\s+");
        for (int i = 0; i < s1.length; i++) {
            String s2 = s1[i];
            if ("<".equals(s2) || ">".equals(s2)) {
                String target;
                String next;
                String var1 = s1[i + 1];
                String var2 = s1[i + 2];
                if ("<".equals(s2)) {
                    target = var1;
                    next = var2;
                } else {
                    target = var2;
                    next = var1;
                }
                //是否冲突
                boolean isConflict = isConflictWithOrderMap(target, next, orderMapList);
                String expression = "(" + s2 + " " + var1 +  " " + var2 + " )";
                if (isConflict) {
                    queue.offer(new Pair("false", ""));
                } else {
                    queue.offer(new Pair<>("true", expression));
                }
                i += 2;
            } else if (!"(".equals(s1[i]) && !")".equals(s1[i]) && !"assert".equals(s1[i]) && !"".equals(s1[i].trim())) {
                queue.offer(new Pair<>(s1[i], s1[i]));
            }
        }

        return queue;
    }

    /**
     * eg.x1 > x2，如果能够找到 x2 > x1 这条路径，则说明冲突,即将next作为起点，target作为终点
     */
    public boolean isConflictWithOrderMap(String target, String next, List<OrderNode> orderMapList) {
        OrderNode targetNode = null;
        OrderNode nextNode = null;
        for (OrderNode orderNode : orderMapList) {
            if (target.equals(orderNode.value)) {
                nextNode = orderNode;
            } else if (next.equals(orderNode.value)) {
                targetNode = orderNode;
            }
        }
        if (targetNode == null || nextNode == null) {
            return false;
        }
        //遍历是否存在该路径
        return canFindRoute(targetNode, nextNode.value, orderMapList);
    }

    public boolean canFindRoute(OrderNode currentNode, String targetValue, List<OrderNode> orderMapList) {
        boolean canFindRoute = false;
        try {
            if (currentNode.largerNodes == null || currentNode.largerNodes.size() == 0) {
                return currentNode.value.equals(targetValue);
            }
            canFindRoute = false;
            for (OrderNode largerNode : currentNode.largerNodes) {
                canFindRoute = canFindRoute || canFindRoute(largerNode, targetValue, orderMapList);

                if (canFindRoute)
                    break;
            }
        } catch (Error e) {
            System.out.println(orderMapList);
            throw e;
        }
        return canFindRoute;
    }

    public List<OrderNode> createOrderMap(List<String[]> variables) {
        List<OrderNode> orderMap = new ArrayList<>();
        for (String[] variable : variables) {
            String option = variable[2];
            String target;
            String next;
            if ("<".equals(option)) {
                target = variable[0];
                next = variable[1];
            } else {
                target = variable[1];
                next = variable[0];
            }
            OrderNode targetNode = null;
            OrderNode nextNode = null;
            for (OrderNode orderNode : orderMap) {
                if (target.equals(orderNode.value)) {
                    targetNode = orderNode;
                } else if (next.equals(orderNode.value)) {
                    nextNode = orderNode;
                }
            }
            if (targetNode == null) {
                targetNode = new OrderNode(target);
                targetNode.largerNodes = new ArrayList<>();
                orderMap.add(targetNode);
            }
            if (nextNode == null) {
                nextNode = new OrderNode(next);
                nextNode.largerNodes = new ArrayList<>();
                orderMap.add(nextNode);
            }
            targetNode.largerNodes.add(nextNode);
        }
        return orderMap;
    }

    static class OrderNode {
        public String value;
        public List<OrderNode> largerNodes;

        public OrderNode(String value) {
            this.value = value;
        }

        public String getLargerNodes() {
            StringBuilder res = new StringBuilder();
            for (OrderNode largerNode : largerNodes) {
                res.append(largerNode.value).append(",");
            }
            return res.toString();
        }

        @Override
        public String toString() {
            return "OrderNode{" +
                    "value='" + value + '\'' +
                    ", largerNodes=" + getLargerNodes() +
                    '}';
        }
    }
}
